package org.haw.vsp.restopoly.services;

import java.util.concurrent.ThreadLocalRandom;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import spark.Request;
import spark.Response;

public class Dice {
	
	public static final String NAME = "DiceService";
	
	public static final String DESCRIPTION = "A service for rolling a dice";
	
	public static final String SERVICE_NAME = "dice";
	
	public static final String SERVICE_URI = "dice";
	
	public static final String YELLOW_PAGE_ID = "4";
	
	public static Integer roll(Request request, Response response) {
		response.status(200);
		response.type("application/json");
		// could be uris
		String player = request.queryParams("player");
		String game = request.queryParams("game");
		int result = (Integer) ThreadLocalRandom.current().nextInt(1, 7);
		try {
			HttpResponse<String> jsonResponse = Unirest.post("http://abs969-events:4567/events")
					.queryString("game", game)
					.queryString("type", "Dice Event")
					.queryString("name", "Dice Event")
					.queryString("reason", player + " rolled a " + result)
					.queryString("resource", request.uri())
					.queryString("player", player)
					.queryString("time", System.currentTimeMillis())
					.asString();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return result;
	}
}
