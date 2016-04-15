package org.haw.vsp.restopoly.entity;

public class Player {

	private String myUser;
	private int myId;
	private Pawn myPawn;
	private String myAccount;
	private boolean myReadiness;
	private String myReadinessService;
	
	public String getUser() {
		return myUser;
	}
	public void setUser(String user) {
		myUser = user;
	}
	public int getId() {
		return myId;
	}
	public void setId(int id) {
		myId = id;
	}
	public Pawn getPawn() {
		return myPawn;
	}
	public void setPawn(Pawn pawn) {
		myPawn = pawn;
	}
	public String getAccount() {
		return myAccount;
	}
	public void setAccount(String account) {
		myAccount = account;
	}
	public boolean isReadiness() {
		return myReadiness;
	}
	public void setReadiness(boolean readiness) {
		myReadiness = readiness;
	}
	public String getReadinessService() {
		return myReadinessService;
	}
	public void setReadinessService(String readinessService) {
		myReadinessService = readinessService;
	}
}
