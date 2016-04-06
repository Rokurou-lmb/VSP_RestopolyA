package org.haw.vsp.restopoly.services;

import java.util.concurrent.ThreadLocalRandom;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import spark.Request;
import spark.Response;

public class Dice {
	private static final String YELLOW_PAGES = "http://http://141.22.34.15/cnt/172.18.0.5/4567/services/1337";

	public static Integer roll(Request request, Response response) {
		response.status(200);
		response.type("application/json");
		// could be uris
		String player = request.queryParams("player");
		String game = request.queryParams("game");
		int result = (Integer) ThreadLocalRandom.current().nextInt(1, 7);
		try {
			HttpResponse<String> jsonResponse = Unirest.post("http://abq335_events:4567/events")
					.queryString("game", game)
					.queryString("type", "Dice Event")
					.queryString("name", "Dice Event")
					.queryString("reason", player + " rolled a " + result)
					.queryString("resource", request.uri())
					.queryString("player", player)
					.queryString("time", System.currentTimeMillis())
					.asString();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
