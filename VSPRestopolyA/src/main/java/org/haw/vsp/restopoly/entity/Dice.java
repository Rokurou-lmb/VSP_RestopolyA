package org.haw.vsp.restopoly.entity;

import java.util.concurrent.ThreadLocalRandom;

import spark.Request;
import spark.Response;

public class Dice {

	public static Integer roll(Request request, Response response) {
		response.status(200);
		response.type("application/json");
		// could be uris
		String player = request.queryParams("player");
		String game = request.queryParams("game");

		return (Integer) ThreadLocalRandom.current().nextInt(1, 7);
	}
}
