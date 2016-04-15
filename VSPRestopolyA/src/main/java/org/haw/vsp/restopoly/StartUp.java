package org.haw.vsp.restopoly;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import org.haw.vsp.restopoly.services.Dice;
import org.haw.vsp.restopoly.services.Users;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class StartUp {

	protected static final String YELLOW_PAGES = "http://172.18.0.5:4567/services";

	protected static final String SERVICE_URI = "http://abl459-services:4567/";

	public static void main(String[] args) {
		get("/hello", (request, response) -> "Hello World");
		get("/dice", Dice::roll);

		registerService(Dice.NAME, Dice.DESCRIPTION, Dice.SERVICE_NAME, Dice.SERVICE_URI, Dice.YELLOW_PAGE_ID);

		get("/users", Users::getUsers);
		post("/users", Users::postUser);
		get("/users/:id", Users::getUser);
		delete("/users/:id", Users::deleteUser);
		put("/users/:id", Users::putUser);

		registerService(Users.NAME, Users.DESCRIPTION, Users.SERVICE_NAME, Users.SERVICE_URI, Users.YELLOW_PAGE_ID);

	}

	private static void registerService(String name, String description, String serviceName, String uri,
			String yellowPageId) {
		try {
			JSONObject json = new JSONObject();
			json.put("name", name);
			json.put("description", description);
			json.put("service", serviceName);
			json.put("uri", SERVICE_URI + uri);
			String yellowURI = YELLOW_PAGES + "/" + yellowPageId;
			String result = Unirest.put(yellowURI).header("Content-Type", "application/json").body(json).asString()
					.getBody();
			System.out.println("Yellow-Pages Registration wurde versendet, an : " + yellowURI + "!");
			System.out.println("Versendetes JSON: " + json.toString());
			System.out.println("Result: " + result);
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
}
