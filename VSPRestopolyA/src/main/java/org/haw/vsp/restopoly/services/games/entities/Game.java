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
	
	/**
	 * @param playerId
	 * @return true if player is ready false otherwise
	 */
	public boolean isPlayerReady(String playerId) {
		return myPlayers.get(playerId).isReady();
	}
	
	/**
	 * Adds a Player to the Game
	 * @param player
	 */
	public void addPlayer(Player player) {
		myPlayers.put(player.getId(), player);
	}
}