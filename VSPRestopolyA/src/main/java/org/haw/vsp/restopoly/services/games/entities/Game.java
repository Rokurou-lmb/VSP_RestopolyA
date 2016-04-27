package org.haw.vsp.restopoly.services.games.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.haw.vsp.restopoly.services.games.Games;

import com.google.gson.JsonObject;

public class Game {
	private String myId;
	private String myName;
	private String myPlayersUri;
	private Map<String, Player> myPlayers;
	private List<Player> myPlayerOrdering;
	private State myGameState;

	private Map<String, String> myServiceUris;
	private Map<String, String> myComponentsUris;
	
	private Player myCurrentPlayer;

	public Game(String id, String name, Map<String, String> serviceUris,
			Map<String, String> componentsUris) {
		myId = id;
		myName = name;
		myPlayersUri = Games.SERVICE_URI + "/" + myId + "/players";
		myServiceUris = serviceUris;
		myComponentsUris = componentsUris;
		
		myPlayers = new HashMap<>();
	}

	public Player getCurrentPlayer() {
		return myCurrentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer){
		myCurrentPlayer = currentPlayer;
	}
	
	public void setPlayerReady(String playerId) throws IllegalArgumentException{
		Player player = myPlayers.get(playerId);
		if (player != null) {
			player.setReady(true);
		} else {
			throw new IllegalArgumentException("Player not found!");
		}
	}
	
	/**
	 * @param playerId
	 * @return true if player is ready false otherwise
	 */
	public boolean isPlayerReady(String playerId) {
		Player player = myPlayers.get(playerId);
		if (player == null) {
			throw new IllegalArgumentException("Player not found!");
		}
		return player.isReady();
	}
	
	/**
	 * Adds a Player to the Game
	 * @param player
	 */
	public void addPlayer(Player player) {
		myPlayers.put(player.getId(), player);
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
	
	public State getStatus() {
		return myGameState;
	}
	
	public void setStatus(State state) {
		if(state == State.RUNNING) {
			myPlayerOrdering = new ArrayList<>(myPlayers.values());
			myCurrentPlayer = myPlayerOrdering.get(0); //TODO maybe use a queue?
		}
	}

	public static String getJsonString(Game game) {
		JsonObject json = new JsonObject();
		json.addProperty("id", game.getId());
		json.addProperty("name", game.getName());
		json.addProperty("players", game.getPlayersUri());
		json.addProperty("services", getServicesAsJsonObject(game));
		json.addProperty("components", getComponentsAsJsonObject(game));
		return json.getAsString();
	}

	public static String getServicesAsJsonObject(Game game) {
		JsonObject json = new JsonObject();
		Set<Entry<String, String>> entries = game.getMyServiceUris().entrySet();
		for (Entry<String, String> entry : entries) {
			json.addProperty(entry.getKey(), entry.getValue());
		}
		return json.getAsString();
	}

	public static String getComponentsAsJsonObject(Game game) {
		JsonObject json = new JsonObject();
		Set<Entry<String, String>> entries = game.getMyComponentsUris().entrySet();
		for (Entry<String, String> entry : entries) {
			json.addProperty(entry.getKey(), entry.getValue());
		}
		return json.getAsString();
	}
}