package org.haw.vsp.restopoly.services.users;

import java.util.HashMap;
import java.util.Map;
import org.haw.vsp.restopoly.services.Service;
import org.haw.vsp.restopoly.services.users.entities.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import spark.Request;
import spark.Response;

public class Users extends Service{
	
	public static final String NAME = "UsersService";
	
	public static final String DESCRIPTION = "A service for managing users";
	
	public static final String SERVICE_NAME = "users";
	
	public static final String SERVICE_URI = "/users";
	
	private static final Gson myGson = new Gson();
	private static final JsonParser myParser = new JsonParser();
	private static Map<String, User> myUsers = new HashMap<>();
	
	/**
	 * Registers a new player with the posted information, then returns the newly registered user as a JSON-String
	 * @return JSON-String representation of the newly registered user
	 */
	public static String postUser(Request request, Response response) {
		JsonObject json = myParser.parse(request.body()).getAsJsonObject();
		String name = json.get("name").getAsString();
		String clientUri = json.get("uri").getAsString();
		String id = SERVICE_URI + "/" + name;
		
		User newUser = new User(name, clientUri, id);
		
		response.status(STATUS_CONFLICT);
		String responseString = "";
		
		if(!myUsers.containsKey(id)) {
			myUsers.put(newUser.getId(), newUser);
			responseString = User.getJsonString(newUser);
			response.status(STATUS_CREATED);
			response.header("Location", id);
		}
		return responseString;
	}
	
	/**
	 * Returns a List of all registered Users as a JSON-String
	 * @return JSON-String of a list containing all registered Users
	 */
	public static String getUsers(Request request, Response response) {
		return myGson.toJson(myUsers.values().stream()
				.map((user) -> user.getId())
				.toArray(String[]::new));
	}
	
	/**
	 * Returns a JSON-String of the user registered under the given ID
	 * @return JSON-String representation of the user registered under the given ID
	 */
	public static String getUser(Request request, Response response) {
		User user = getUserById(request);
		response.status(STATUS_NOT_FOUND);
		String responseString = "Der Benutzer konnte nicht gefunden werden.";
		if(user != null) {
			response.status(STATUS_OK);
			responseString = User.getJsonString(user);
		}
		return responseString;
	}
	
	/**
	 * Registers a new player with the given information and the given ID, then returns the newly registered user as a
	 * JSON-String. If one of the or all parameters were empty, the existing data is used.
	 * @return empty string
	 */
	public static String putUser(Request request, Response response) {
		JsonObject json = myParser.parse(request.body()).getAsJsonObject();
		User newUser;
		String newId = parseUserIdFromRequest(request);
		String newName = json.get("name").getAsString();
		String newClientUri = json.get("uri").getAsString();
		response.status(STATUS_CREATED);
		
		if(myUsers.containsKey(newId)) {
			response.status(STATUS_OK);
			User user = myUsers.get(newId);
			
			newId = ((newId == null) ? user.getId() : newId);
			newName = ((newName == null) ? user.getName() : newName);
			newClientUri = ((newClientUri == null) ? user.getClientUri() : newClientUri);
		}
		newUser = new User(newName, newClientUri, newId);
		myUsers.put(newId, newUser);
		response.header("Location", newUser.getId());
		return Service.NO_RESPONSE;
	}
	
	/**
	 * Unregisters the user corresponding to the given ID.
	 * If no user is registered for this ID, nothing happens.
	 * @return empty string
	 */
	public static String deleteUser(Request request, Response response) {
		String userId = parseUserIdFromRequest(request);
		myUsers.remove(userId);
		return Service.NO_RESPONSE;
	}
	
	/**
	 * Parses the id from the request and returns the user corresponding to it.
	 * @param request the Http-Request from which the id should be parsed
	 * @return The User corresponding to the id or {@code null} if no user was found
	 */
	private static User getUserById(Request request) {
		String userId = parseUserIdFromRequest(request);
		User user = myUsers.get(userId);
		return user;
	}

	/**
	 * Parses the user-id from the request.
	 * @param request the Http-Request from which the id should be parsed
	 * @return the userId
	 */
	private static String parseUserIdFromRequest(Request request) {
		return "/users/" + request.params(":id");
	}

	public static String getName() {
		return NAME + " " + GROUP_NAME;
	}
}
