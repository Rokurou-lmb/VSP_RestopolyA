package org.haw.vsp.restopoly.services.games;

import java.util.HashMap;
import java.util.Map;
import org.haw.vsp.restopoly.services.Service;
import org.haw.vsp.restopoly.services.games.entities.Game;
import org.haw.vsp.restopoly.services.games.entities.Player;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

public class Games {
	private static Map<String, Game> myGames = new HashMap<>();
	private static Gson myGson = new Gson();

	public Games() {
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
		Player newPlayer = myGson.fromJson(request.body(), Player.class);

		newPlayer.setReady((newPlayer.isReady() == null) ? false : newPlayer.isReady());
		newPlayer.setPawn((newPlayer.getPawn() == null) ? getNewDefaultPawn() : newPlayer.getPawn());
		newPlayer.setAccount((newPlayer.getAccount() == null) ? getNewAccount() : newPlayer.getAccount() );
		
		Game game = myGames.get(gameId);
		game.addPlayer(newPlayer);
		return Service.NO_RESPONSE;
	}

	public static String putPlayerIsReady(Request request, Response response) {
		String gameId = request.params(":gameId");
		String playerId = request.params(":playerId");
		
		Game game = myGames.get(gameId);
		game.setPlayerReady(playerId);
		
		return Service.NO_RESPONSE;
	}

	public static String getPlayerIsReady(Request request, Response response) {
		String gameId = request.params(":gameId");
		String playerId = request.params(":playerId");
		
		Game game = myGames.get(gameId);
		Boolean ready = game.isPlayerReady(playerId);
		
		return myGson.toJson(ready);
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
		boolean ready;
		try {
			game.setPlayerReady(playerId);
			ready = true;
		} catch (IllegalArgumentException e) {
			ready = false;
		}

		return myGson.toJson(ready);
	}
	
	/**
	 * Creates a new bankaccount and returns the uri referencing it
	 * @return uri to newly created bankaccount
	 */
	private static String getNewAccount() {
		return null;
		//TODO implement
	}
	
	/**
	 * Creates a new pawn and returns the uri referencing it
	 * @return uri to newly created pawn
	 */
	private static String getNewDefaultPawn() {
		return null;
		//TODO implement
	}
}
