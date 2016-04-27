package org.haw.vsp.restopoly.services.users.entities;

import com.google.gson.JsonObject;

public class User{

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

	public String getClientUri() {
		return myClientUri;
	}

	public void setClientUri(String clientUri) {
		myClientUri = clientUri;
	}
	
	public static String getJsonString(User user) {
		JsonObject json = new JsonObject();
		json.addProperty("id", user.getId());
		json.addProperty("name", user.getName());
		json.addProperty("uri", user.getClientUri());
		return json.getAsString();
	}
}
