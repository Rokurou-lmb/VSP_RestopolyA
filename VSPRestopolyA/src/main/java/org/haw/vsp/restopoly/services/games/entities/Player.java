package org.haw.vsp.restopoly.services.games.entities;

public class Player {

	private String myUser;
	private String myId;
	private String myPawn;
	private String myAccount;
	private Boolean myReadiness;
	private String myReadinessService;
	
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
}
