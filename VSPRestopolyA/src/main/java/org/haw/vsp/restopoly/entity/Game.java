package org.haw.vsp.restopoly.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Game {
	private String myId;
	private Map<String, Player> myPlayers;
	private boolean myGameStarted;
	private Set<String> myServicesUris;
	private Set<String> myComponentsUris;
	private Player myCurrentPlayer;
	
	public Game() {
		myId = "0";
		myPlayers = new HashMap<>();
		myGameStarted = false;
		myServicesUris = new HashSet<>();
		myComponentsUris = new HashSet<>();
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
			player.setReadiness(true);
		} else {
			throw new IllegalArgumentException("Player not found!");
		}
	}
}