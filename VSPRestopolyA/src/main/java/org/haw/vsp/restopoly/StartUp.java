package org.haw.vsp.restopoly;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import org.haw.vsp.restopoly.services.dice.Dice;
import org.haw.vsp.restopoly.services.games.Games;
import org.haw.vsp.restopoly.services.users.Users;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class StartUp {

	protected static final String YELLOW_PAGES = "http://172.18.0.5:4567/services";

	protected static final String SERVICE_URI = "http://abl459-services:4567";

	public static void main(String[] args) {
		//Hello World
		get("/hello", (request, response) -> "Hello World");

		//Dice-Service
		get("/dice", Dice::roll);

		registerService(Dice.NAME, Dice.DESCRIPTION, Dice.SERVICE_NAME, Dice.SERVICE_URI);

		//Users-Service
		get("/users", Users::getUsers);
		post("/users", Users::postUser);
		get("/users/:id", Users::getUser);
		delete("/users/:id", Users::deleteUser);
		put("/users/:id", Users::putUser);

		registerService(Users.NAME, Users.DESCRIPTION, Users.SERVICE_NAME, Users.SERVICE_URI);

		//Games-Service
		get("/games", Games::getGames);
		post("/games", Games::postGame);
		get("/games/:gameId", Games::getGame);
		get("/games/:gameId/players", Games::getPlayers);
		post("/games/:gameId/players", Games::postPlayer);
		get("/games/:gameId/players/:playerId", Games::getPlayer);
		put("/games/:gameId/players/:playerId/ready", Games::putPlayerIsReady);
		get("/games/:gameId/players/:playerId/ready", Games::getPlayerIsReady);
		get("/games/:gameId/players/current", Games::getCurrentPlayer);
		
		registerService(Games.NAME, Games.DESCRIPTION, Games.SERVICE_NAME, Games.SERVICE_URI);
	}

	private static void registerService(String name, String description, String serviceName, String uri) {
		try {
			JSONObject json = new JSONObject();
			json.put("name", name);
			json.put("description", description);
			json.put("service", serviceName);
			json.put("uri", SERVICE_URI + uri);
			String yellowURI = YELLOW_PAGES;
			String result = Unirest.post(yellowURI).header("Content-Type", "application/json").body(json).asString()
					.getBody();
			System.out.println("Yellow-Pages Registration wurde versendet, an : " + yellowURI + "!");
			System.out.println("Versendetes JSON: " + json.toString());
			System.out.println("Result: " + result);
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
}
