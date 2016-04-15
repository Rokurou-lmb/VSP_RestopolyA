package org.haw.vsp.restopoly.entity;

public class User {

	private String myId;
	private String myName;
	private String myClientUri;

	public User(String name, String clientUri, String id) {
		myId = id;
		myName = name;
		myClientUri = clientUri;
	}

	public String getId() {
		return myId;
	}

	public void setId(String id) {
		myId = id;
	}

	public String getName() {
		return myName;
	}

	public void setName(String name) {
		myName = name;
	}

	public String getUri() {
		return myClientUri;
	}

	public void setUri(String clientUri) {
		myClientUri = clientUri;
	}

}
