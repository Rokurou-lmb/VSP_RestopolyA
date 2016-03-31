package org.haw.vsp.restopoly.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.haw.vsp.restopoly.entity.User;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;

public class Users {
	
	private static Gson myGson = new Gson();
	private static int myIdTracker = 0;
	private static Map<Integer, User> myUsers = new HashMap<>();

	public static String postUser(Request request, Response response) {
		while(myUsers.containsKey(myIdTracker)) {
			myIdTracker++;
		}
		String playerName = request.queryParams("name");
		String playerUri = request.queryParams("uri");
		User newUser = new User(playerName, playerUri, myIdTracker);
		myUsers.put(myIdTracker, newUser);
		myIdTracker++;
		return myGson.toJson(newUser);
	}
	
	public static String getUsers(Request request, Response response) {
		List<User> users = new ArrayList<>(myUsers.values());
		return myGson.toJson(users);
	}

	public static String putUser(Request request, Response response) {
		Integer playerId = Integer.parseInt(request.params(":id"));
		String playerName = request.queryParams("name");
		String playerUri = request.queryParams("uri");
		if(myUsers.containsKey(playerId)) {
			User user = myUsers.get(playerId);
			user.setMyName(playerName);
			user.setMyUri(playerUri);
		} else {
			User newUser = new User(playerName, playerUri, playerId);
			myUsers.put(playerId, newUser);
		}
		
		return "";
	}
	
	public static String getUser(Request request, Response response) {
		User user = getUserById(request);
		return myGson.toJson(user);
	}
	
	public static String deleteUser(Request request, Response response) {
		int userId = parseUserIdFromRequest(request);
		myUsers.remove(userId);
		return "";
	}
	
	private static User getUserById(Request request) {
		int userId = parseUserIdFromRequest(request);
		return myUsers.get(userId);
	}
	
	private static int parseUserIdFromRequest(Request request) {
		return Integer.parseInt(request.params(":id"));
	}
	
	
}
