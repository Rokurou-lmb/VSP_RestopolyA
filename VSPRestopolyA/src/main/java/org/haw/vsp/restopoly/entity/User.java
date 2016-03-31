package org.haw.vsp.restopoly.entity;

public class User {

	private int myId;
	private String myName;
	private String myUri;



	
	public User(String name, String uri, int id) {
		myId = id; 
		myName = name;
		myUri = uri;
	}

	public int getMyId() {
		return myId;
	}

	public void setMyId(int myId) {
		this.myId = myId;
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public String getMyUri() {
		return myUri;
	}

	public void setMyUri(String myUri) {
		this.myUri = myUri;
	}

}
