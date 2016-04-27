package org.haw.vsp.restopoly.services.games.entities;

import java.util.HashMap;
import java.util.Map;

public class Game {
	private String myId;
	private String name;
	private Map<String, Player> myPlayers;
	private boolean myGameStarted;
	private Map<String, String> myServicesUris;
//	private Set<String> myComponentsUris; //TODO find out if components are really needed?
	private Player myCurrentPlayer;
	
	public Game() {
		myId = "0";
		myPlayers = new HashMap<>();
		myGameStarted = false;
		myServicesUris = new HashMap<>();
//		myComponentsUris = new HashSet<>();
	}

	public Player getCurrentPlayer() {
		return myCurrentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
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
	
	public boolean isPlayerReady(String playerId) {
		return myPlayers.get(playerId).isReady();
	}
	
	public void addPlayer(Player player) {
		myPlayers.put(player.getId(), player);
	}
}