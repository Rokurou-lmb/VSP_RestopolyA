package org.haw.vsp.restopoly;

import org.haw.vsp.restopoly.services.Dice;
import org.haw.vsp.restopoly.services.Users;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import static spark.Spark.*;

public class StartUp {
	
	private static final String YELLOW_PAGES = "http://172.18.0.5:4567/services";
	
	public static void main(String[] args) {
		get("/hello", (request, response) -> "Hello World");
		get("/dice", Dice::roll);
		//Dice service registration with Yellow Pages
		try {
			JSONObject json = new JSONObject();
			json.put("name", "DiceService");
			json.put("description", "A service for rolling a dice");
			json.put("service", "dice");
			json.put("uri", "http://abl459_docker_0:4567/dice");
			String result = Unirest.put(YELLOW_PAGES + "/1")
				.header("Content-Type", "application/json")
				.body(json)
				.asString().getBody();
			System.out.println("Yellow-Pages Registration wurde versendet!");
			System.out.println("Versendetes JSON: " + json.toString());
			System.out.println("Result: " + result);
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		
		get("/users", Users::getUsers);
		post("/users", Users::postUser);
		get("/users/:id", Users::getUser);
		delete("/users/:id", Users::deleteUser);
		put("/users/:id", Users::putUser);
		//Users service registration with Yellow Pages
		try {
			JSONObject json = new JSONObject();
			json.put("name", "UsersService");
			json.put("description", "A service for managing users");
			json.put("service", "users");
			json.put("uri", "http://abl459_docker_0:4567/users");
			String result = Unirest.put(YELLOW_PAGES + "/2")
				.header("Content-Type", "application/json")
				.body(json)
				.asString().getBody();
			System.out.println("Yellow-Pages Registration wurde versendet!");
			System.out.println("Versendetes JSON: " + json.toString());
			System.out.println("Result: " + result);
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
}
