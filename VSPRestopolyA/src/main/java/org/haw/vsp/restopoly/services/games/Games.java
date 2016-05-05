package org.haw.vsp.restopoly.services.games;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.haw.vsp.restopoly.services.Service;
import org.haw.vsp.restopoly.services.games.entities.Game;
import org.haw.vsp.restopoly.services.games.entities.Player;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import spark.Request;
import spark.Response;

public class Games extends Service {

	public static final String NAME = "GamesService";

	public static final String DESCRIPTION = "A service for managing games";

	public static final String SERVICE_NAME = "games";

	public static final String SERVICE_URI = "/games";

	private static Map<String, Game> myGames = new HashMap<>();
	private static Gson myGson = new Gson();
	private static JsonParser myParser = new JsonParser();

	public static String getGames(Request request, Response response) {
		return myGson.toJson(myGames.values().stream()
				.map((game) -> Game.getJsonString(game))
				.toArray(String[]::new));
	}

	public static String postGame(Request request, Response response) {
		JsonObject json = myParser.parse(request.body()).getAsJsonObject();
		String id = getJsonAttribute(json, "id");
		String name = getJsonAttribute(json, "name");

		JsonObject servicesJson = json.get("services").getAsJsonObject();
		JsonObject componentsJson = json.get("components").getAsJsonObject();

		Map<String, String> services = getMapFromJson(servicesJson);
		Map<String, String> components = getMapFromJson(componentsJson);

		Game newGame = new Game(id, name, services, components);
		myGames.put(newGame.getId(), newGame);

		response.status(STATUS_CREATED);
		response.header("Location", newGame.getId());
		return "";
	}

	public static String getGame(Request request, Response response) {
		String gameId = request.params(":gameId");

		String responseString = "";
		try {
			Game game = getGameById(gameId);
			responseString = Game.getJsonString(game);
		} catch (IllegalArgumentException e) {
			response.status(STATUS_NOT_FOUND);
		}
		return responseString;
	}
	
	public static String getStatus(Request request, Response response) {
		String gameId = request.params("gameId");
		String responseString = "";
		try {
			Game game = getGameById(gameId);
			responseString = game.getStatus().toString();
		} catch (IllegalArgumentException e) {
			response.status(STATUS_NOT_FOUND);
		}
		return responseString;
	}
	
	public static String putStatus(Request request, Response response) {
		response.status(INTERNAL_SERVER_ERROR);
		return ""; //TODO implement
	}

	public static String getPlayers(Request request, Response response) {
		String gameId = request.params(":gameId");

		String responseString = "";
		try {
			Game game = getGameById(gameId);
			Collection<Player> players = game.getPlayers();
			responseString = myGson.toJson(players.stream()
					.map((player) -> player.getId())
					.toArray());
		} catch (IllegalArgumentException e) {
			response.status(STATUS_NOT_FOUND);
		}
		return responseString;
	}

	public static String postPlayer(Request request, Response response) {
		String gameId = request.params(":gameId");
		JsonObject json = myParser.parse(request.body()).getAsJsonObject();
		String id = getJsonAttribute(json, "id");
		String user = getJsonAttribute(json, "user");
		String pawn = getJsonAttribute(json, "pawn");
		String account = getJsonAttribute(json, "account");

		pawn = (pawn == null) ? getNewDefaultPawn() : pawn;
		account = (account == null) ? getNewAccount() : account;

		Player newPlayer = new Player(user, id, pawn, account);

		response.status(STATUS_CREATED);
		response.header("Location", newPlayer.getId()); // TODO give fully
														// classified uri (test
														// if spark fully
														// qualifies relative
														// uris)

		try {
			Game game = getGameById(gameId);
			game.addPlayer(newPlayer);
		} catch (IllegalArgumentException e) {
			response.status(STATUS_NOT_FOUND);
		}
		return NO_RESPONSE;
	}

	public static String getPlayer(Request request, Response response) {
		String gameId = request.params(":gameId");
		String playerId = request.params(":playerId");

		String responseString = "";
		try {
			Game game = getGameById(gameId);
			Player player = game.getPlayer(playerId);
			responseString = Player.getJsonString(player);
		} catch (IllegalArgumentException e) {
			response.status(STATUS_NOT_FOUND);
		}
		return responseString;
	}

	public static String getPlayerIsReady(Request request, Response response) {
		String gameId = request.params(":gameId");
		String playerId = request.params(":playerId");

		String responseString = "";
		Boolean ready;
		try {
			Game game = getGameById(gameId);
			ready = game.isPlayerReady(playerId);
			responseString = myGson.toJson(ready);
		} catch (IllegalArgumentException e) {
			response.status(STATUS_NOT_FOUND);
		}

		return responseString;
	}

	public static String putPlayerIsReady(Request request, Response response) {
		String gameId = request.params(":gameId");
		String playerId = request.params(":playerId");

		try {
			Game game = getGameById(gameId);
			game.setPlayerReady(playerId);
			boolean allPlayersReady = game.getPlayers().stream()
					.allMatch((player) -> player.isReady());
			if(allPlayersReady) {
				//put status to running
			}
		} catch (IllegalArgumentException e) {
			response.status(STATUS_NOT_FOUND);
		}
		return NO_RESPONSE;
	}

	public static String getCurrentPlayer(Request request, Response response) {
		String gameId = request.params(":gameId");
		Player player = null;
		try {
			Game game = getGameById(gameId);
			player = game.getCurrentPlayer();
		} catch (IllegalArgumentException e) {
			response.status(STATUS_NOT_FOUND);
		}
		return Player.getJsonString(player);
	}

	private static Map<String, String> getMapFromJson(JsonObject json) {
		Map<String, String> map = new HashMap<>();

		for (Entry<String, JsonElement> jsonEntry : json.entrySet()) {
			map.put(jsonEntry.getKey(), jsonEntry.getValue().getAsString());
		}
		return map;
	}

	/**
	 * Creates a new bankaccount and returns the uri referencing it
	 * 
	 * @return uri to newly created bankaccount
	 */
	private static String getNewAccount() {
		return null;
		// TODO implement
	}

	/**
	 * Creates a new pawn and returns the uri referencing it
	 * 
	 * @return uri to newly created pawn
	 */
	private static String getNewDefaultPawn() {
		return null;
		// TODO implement
	}

	/**
	 * Gets the attribute of the given identifier from {@code json}
	 * 
	 * @param json
	 * @param identifier
	 * @return The attribute, or {@code null} if none was found.
	 */
	private static String getJsonAttribute(JsonObject json, String identifier) {
		return json.get(identifier).getAsString();
	}

	private static Game getGameById(String gameId) throws IllegalArgumentException {
		Game game = myGames.get(gameId);
		if (game == null) {
			throw new IllegalArgumentException();
		}
		return game;
	}
}
