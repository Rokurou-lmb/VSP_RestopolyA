package org.haw.vsp.restopoly.services.boards.entities;

import com.google.gson.JsonObject;

public class Pawn {
	private final String myId;
	private String myPlace;
	private String myPosition;
	
	public Pawn(String id) {
		myId = id;
		myPlace = "";
		myPosition = "";
	}

	public String getPlace() {
		return myPlace;
	}

	public void setPlace(String place) {
		myPlace = place;
	}

	public String getPosition() {
		return myPosition;
	}

	public void setPosition(String position) {
		myPosition = position;
	}

	public String getId() {
		return myId;
	}
	
	private static String getJsonString(Pawn pawn) {
		JsonObject json = new JsonObject();
		json.addProperty("", "");
		return json.getAsString();
	}
	
}
