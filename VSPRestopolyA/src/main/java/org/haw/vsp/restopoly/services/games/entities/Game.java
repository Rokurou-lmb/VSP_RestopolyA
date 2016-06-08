package org.haw.vsp.restopoly.services.games.entities;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

import org.haw.vsp.restopoly.services.MissingServiceException;
import org.haw.vsp.restopoly.services.games.Games;
import org.haw.vsp.restopoly.services.games.GamesRestClient;

import com.google.gson.JsonObject;

public class Game {
	private String myId;
	private String myName;
	private String myPlayersUri;
	private String myServicesUri;
	private String myComponentsUri;
	private Map<String, Player> myPlayers;
	private Queue<Player> myPlayerOrdering;
	private State myGameState;

	private Map<String, String> myServiceUris;
	private Map<String, String> myComponentsUris;
	
	private Player myCurrentPlayer;

	public Game(String id, String name, Map<String, String> serviceUris, Map<String, String> componentsUris, boolean running) {
		myId = id;
		myName = name;
		myPlayersUri = myId + "/players";
		myServicesUri = myId + "/services";
		myComponentsUri = myId + "/components";
		myServiceUris = serviceUris;
		myComponentsUris = componentsUris;
		myGameState = State.REGISTRATION;
		
		myPlayers = new HashMap<>();
	}

	public Game(String id, String name, Map<String, String> serviceUris, Map<String, String> componentsUris) {
		this(id, name, serviceUris, componentsUris, false);
	}

	public Player getCurrentPlayer() {
		return myCurrentPlayer;
	}
	
	public void setPlayerReady(String playerId) throws IllegalArgumentException{
		Player player = myPlayers.get(playerId);
		if(player == null) {
			throw new IllegalArgumentException("Player not found!");
		}else if(myGameState == State.RUNNING && player == myCurrentPlayer) {
			nextCurrentPlayer();
		} else if(myGameState == State.REGISTRATION) {
			player.setReady(true);
		}
	}
	
	/**
	 * @param playerId
	 * @return {@code true} if player is ready {@code false} otherwise
	 */
	public boolean isPlayerReady(String playerId) {
		Player player = myPlayers.get(playerId);
		if (player == null) {
			throw new IllegalArgumentException("Player not found!");
		}
		return player.isReady();
	}
	
	/**
	 * Adds a Player to the Game, will only add a new player if the game is currently in the Registration {@link State}
	 * @param player
	 */
	public void addPlayer(Player player) {
		if(myGameState == State.REGISTRATION) {
			myPlayers.put(player.getId(), player);
		}
	}
	
	public String getPlayersUri() {
		return myPlayersUri;
	}

	public String getName() {
		return myName;
	}

	public String getId() {
		return myId;
	}
	
	public Map<String, String> getMyServiceUris() {
		return myServiceUris;
	}

	public Map<String, String> getMyComponentsUris() {
		return myComponentsUris;
	}
	
	public Collection<Player> getPlayers() {
		return myPlayers.values();
	}
	
	public Player getPlayer(String playerId) {
		return myPlayers.get(playerId);
	}
	
	public String getServicesUri() {
		return myServicesUri;
	}

	public String getComponentsUri() {
		return myComponentsUri;
	}
	
	/**
	 * Current {@link State} of the game
	 * @return
	 */
	public State getState() {
		return myGameState;
	}
	
	public void advanceState() {
		myGameState = myGameState.nextStatus();
		
		if(myGameState == State.RUNNING) {
			Collection<Player> players = myPlayers.values();
			myPlayerOrdering = new ArrayDeque<>(players);
			myCurrentPlayer = myPlayerOrdering.peek();
			for (Player player : players) {
				player.setReady(false);
			}
		}
	}
	
	public static Game fromUriJson(JsonObject json) { //TODO implement json to Game parsing
		Game game = null;
		try {
			GamesRestClient gameRestClient = Games.getGamesRestClient();
			
			
			String id = json.get("id").toString();
			String name = json.get("name").toString();
			String playersUri = json.get("players").toString();
			Collection<Player> players = gameRestClient.getPlayers(id);
			
			String servicesUri = json.get("services").toString();
			Map<String, String> services = gameRestClient.getServices(servicesUri);
			
			String componentsUri = json.get("components").toString();
			Map<String, String> components = gameRestClient.getComponents(componentsUri);
			
			String started = json.get("started").toString();
			
			game = new Game(id, name, services, components, Boolean.getBoolean(started));
		} catch (MissingServiceException e) {
			System.out.println("Games Service not found!");
		}
		
		return game;
	}
	
	public static String getUriJsonString(Game game) {
		JsonObject json = new JsonObject();
		json.addProperty("id", game.getId());
		json.addProperty("name", game.getName());
		json.addProperty("players", game.getPlayersUri());
		json.addProperty("services", game.getServicesUri());
		json.addProperty("components", game.getComponentsUri());
		json.addProperty("started", game.getState() == State.RUNNING);
		return json.toString();
	}

	public static String getJsonString(Game game) {
		JsonObject json = new JsonObject();
		json.addProperty("id", game.getId());
		json.addProperty("name", game.getName());
		json.addProperty("players", game.getPlayersUri());
		json.addProperty("services", getServicesAsJsonObject(game));
		json.addProperty("components", getComponentsAsJsonObject(game));
		return json.toString();
	}

	public static String getJsonSummaryString(Game game) {
		JsonObject json = new JsonObject();
		json.addProperty("id", game.getId());
		json.addProperty("players", game.getPlayersUri());
		return json.toString();
	}

	public static String getServicesAsJsonObject(Game game) {
		JsonObject json = new JsonObject();
		Set<Entry<String, String>> entries = game.getMyServiceUris().entrySet();
		for (Entry<String, String> entry : entries) {
			json.addProperty(entry.getKey(), entry.getValue());
		}
		return json.toString();
	}

	private static String getComponentsAsJsonObject(Game game) {
		JsonObject json = new JsonObject();
		Set<Entry<String, String>> entries = game.getMyComponentsUris().entrySet();
		for (Entry<String, String> entry : entries) {
			json.addProperty(entry.getKey(), entry.getValue());
		}
		return json.toString();
	}
	
	private void nextCurrentPlayer() {
		Player lastPlayer = myCurrentPlayer;
		myCurrentPlayer = myPlayerOrdering.poll();
		myPlayerOrdering.add(lastPlayer);
	}
}