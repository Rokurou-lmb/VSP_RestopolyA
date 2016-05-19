package org.haw.vsp.restopoly.services.boards.entities;

import com.google.gson.JsonObject;

public class Pawn {
	private final String myId;
	private String myPlayer;
	private String myPlace;
	private int myPosition;
	private String myRolls;
	private String myMoves;
	
	public Pawn(String id, String player, String place, int position, String rolls, String moves) {
		myId = id;
		myPlayer = player;
		myPlace = place;
		myPosition = 0;
		myRolls = rolls;
		myMoves = moves;
	}
	
	public String getPlayer() {
		return myPlayer;
	}
	
	public void setPlayer(String player) {
		myPlayer = player;
	}

	public String getPlace() {
		return myPlace;
	}

	public void setPlace(String place) {
		myPlace = place;
	}

	public int getPosition() {
		return myPosition;
	}

	public void setPosition(int position) {
		myPosition = position;
	}

	public String getRolls() {
		return myRolls;
	}

	public void setRolls(String rolls) {
		myRolls = rolls;
	}

	public String getMoves() {
		return myMoves;
	}

	public void setMoves(String moves) {
		myMoves = moves;
	}

	public String getId() {
		return myId;
	}
	
	public static String getJsonString(Pawn pawn) {
		JsonObject json = new JsonObject();
		json.addProperty("id", pawn.getId());
		json.addProperty("player", pawn.getPlayer());
		json.addProperty("place", pawn.getPlace());
		json.addProperty("position", pawn.getPosition());
		json.addProperty("roll", pawn.getRolls());
		json.addProperty("move", pawn.getMoves());
		return json.toString();
	}
}
