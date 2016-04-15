package org.haw.vsp.restopoly.services.games;

import java.util.HashMap;
import java.util.Map;

import org.haw.vsp.restopoly.entity.Board;
import org.haw.vsp.restopoly.entity.Game;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;

public class Games {
	private static Map<String, Game> myGames = new HashMap<>();
	private static Gson myGson = new Gson();
	private Board board;

	public Games() {
		board = new Board();
	}

	public static String getGames(Request request, Response response) {
		return myGson.toJson(myGames.values());
	}

	public static String postGames(Request request, Response response) {
		Game game = new Game();
		Game newGame = myGson.fromJson(request.body(), Game.class);
		//TODO: create new game with optional parameters
		response.status(201);
		return "";
	}

	public static String createPlayer(Request request, Response response) {
		String gameId = request.params(":gameId");

		return "";
	}

	public static String putPlayerIsReady(Request request, Response response) {
		String gameId = request.params(":gameId");
		String playerId = request.params(":playerId");

		return "";
	}

	public static String getPlayerIsReady(Request request, Response response) {
		String gameId = request.params(":gameId");
		String playerId = request.params(":playerId");

		return "";
	}

	public static String getCurrentPlayer(Request request, Response response) {
		String gameId = request.params(":gameId");
		Game game = myGames.get(gameId);

		return myGson.toJson(game.getCurrentPlayer());
	}

	public static String setPlayerReady(Request request, Response response) {
		String gameId = request.params(":gameId");
		String playerId = request.params(":playerId");

		Game game = myGames.get(gameId);
		Boolean ready;
		try {
			game.setPlayerReady(playerId);
			ready = new Boolean(true);
		} catch (IllegalArgumentException e) {
			ready = new Boolean(false);
		}

		return myGson.toJson(ready);
	}

}
