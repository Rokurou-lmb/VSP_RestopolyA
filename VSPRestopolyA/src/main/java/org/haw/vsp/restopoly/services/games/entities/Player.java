package org.haw.vsp.restopoly.services.games.entities;

import org.haw.vsp.restopoly.services.games.Games;

import com.google.gson.JsonObject;

public class Player {

	private String myUser;
	private String myId;
	private String myPawn;
	private String myAccount;
	private Boolean myReadiness;
	private String myReadinessService;
	
	public Player(String user, String id, String pawn, String account) {
		myUser = user;
		myId = id;
		myPawn = pawn;
		myAccount = account;
		myReadiness = false;
		myReadinessService = Games.SERVICE_URI + "/players/" + myId + "/ready";
	}
	
	public String getUser() {
		return myUser;
	}
	public void setUser(String user) {
		myUser = user;
	}
	public String getId() {
		return myId;
	}
	public void setId(String id) {
		myId = id;
	}
	public String getPawn() {
		return myPawn;
	}
	public void setPawn(String pawn) {
		myPawn = pawn;
	}
	public String getAccount() {
		return myAccount;
	}
	public void setAccount(String account) {
		myAccount = account;
	}
	public Boolean isReady() {
		return myReadiness;
	}
	public void setReady(Boolean readiness) {
		myReadiness = readiness;
	}
	public String getReadinessService() {
		return myReadinessService;
	}
	public void setReadinessService(String readinessService) {
		myReadinessService = readinessService;
	}

	public static String getJsonString(Player player) {
		JsonObject json = new JsonObject();
		json.addProperty("id", player.getId());
		json.addProperty("user", player.getUser());
		json.addProperty("pawn", player.getPawn());
		json.addProperty("account", player.getAccount());
		json.addProperty("ready", player.getReadinessService());
		
		return json.getAsString();
	}
}
