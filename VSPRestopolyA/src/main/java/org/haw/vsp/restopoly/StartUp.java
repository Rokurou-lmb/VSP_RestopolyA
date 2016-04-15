package org.haw.vsp.restopoly;

import org.haw.vsp.restopoly.services.Dice;
import org.haw.vsp.restopoly.services.Users;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import static spark.Spark.*;

public class StartUp {

	private static final String YELLOW_PAGES = "http://172.18.0.5:4567/services";

	private static final String SERVICE_URI = "http://abl459-services:4567/";

	public static void main(String[] args) {
		get("/hello", (request, response) -> "Hello World");
		get("/dice", Dice::roll);

		registerService("DiceService", "A service for rolling a dice", "dice", "dice", 4);

		get("/users", Users::getUsers);
		post("/users", Users::postUser);
		get("/users/:id", Users::getUser);
		delete("/users/:id", Users::deleteUser);
		put("/users/:id", Users::putUser);

		registerService("UsersService", "A service for managing users", "users", "users", 2);

	}

	private static void registerService(String name, String description, String service, String uri, int id) {
		try {
			JSONObject json = new JSONObject();
			json.put("name", name);
			json.put("description", description);
			json.put("service", service);
			json.put("uri", SERVICE_URI + uri);
			String yellowURI = YELLOW_PAGES + "/" +Integer.toString(id);
			String result = Unirest.put(yellowURI).header("Content-Type", "application/json")
					.body(json).asString().getBody();
			System.out.println("Yellow-Pages Registration wurde versendet, an : " + yellowURI + "!");
			System.out.println("Versendetes JSON: " + json.toString());
			System.out.println("Result: " + result);
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
}
