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
	
	public static final String NAME = "UsersService";
	
	public static final String DESCRIPTION = "A service for managing users";
	
	public static final String SERVICE_NAME = "users";
	
	public static final String SERVICE_URI = "users";
	
	public static final String YELLOW_PAGE_ID = "2";

	private static Gson myGson = new Gson();
	private static Map<String, User> myUsers = new HashMap<>();

	/**
	 * Registers a new player with the posted information, then returns the
	 * newly registered user as a JSON-String
	 * 
	 * @return JSON-String representation of the newly registered user
	 */
	public static String postUser(Request request, Response response) {
		User newUser = myGson.fromJson(request.body(), User.class);
		myUsers.put(newUser.getId(), newUser);
		return myGson.toJson(newUser);
	}

	/**
	 * Returns a List of all registered Users as a JSON-String
	 * 
	 * @return JSON-String of a list containing all registered Users
	 */
	public static String getUsers(Request request, Response response) {
		List<User> users = new ArrayList<>(myUsers.values());
		return myGson.toJson(users);
	}

	/**
	 * Registers a new player with the given information and the given ID, then
	 * returns the newly registered user as a JSON-String. If one of the or all parameters were empty, the existing data is used.
	 * 
	 * @return empty String
	 */
	public static String putUser(Request request, Response response) {
		User newUser = myGson.fromJson(request.body(), User.class);

		String newPlayerId = request.params(":id");
		String newPlayerName = newUser.getName();
		String newPlayerUri = newUser.getUri();

		if (myUsers.containsKey(newPlayerId)) {
			User user = myUsers.get(newPlayerId);

			//TODO find out if Gson initializes with empty String or null
			user.setId((newPlayerId == null || newPlayerId.equals("")) ? user.getId() : newPlayerId);
			user.setName((newPlayerName == null || newPlayerName.equals("")) ? user.getName() : newPlayerName);
			user.setUri((newPlayerUri == null || newPlayerUri.equals("")) ? user.getUri() : newPlayerUri);
		}

		return "";
	}

	/**
	 * Returns a JSON-String of the user registered under the given ID
	 * 
	 * @return JSON-String representation of the user registered under the given
	 *         ID
	 */
	public static String getUser(Request request, Response response) {
		User user = getUserById(request);
		return (user == null) ? "" : myGson.toJson(user);
	}

	/**
	 * Unregisters the user corresponding to the given ID. If no user is
	 * registered for this ID, nothing happens.
	 * 
	 * @return
	 */
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
