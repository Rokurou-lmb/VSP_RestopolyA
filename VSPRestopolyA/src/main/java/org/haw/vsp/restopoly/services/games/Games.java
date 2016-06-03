package org.haw.vsp.restopoly.services.games;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.haw.vs.praktikum.gwln.yellowpages.YellowPagesRestClient;
import org.haw.vsp.restopoly.services.MissingServiceException;
import org.haw.vsp.restopoly.services.Service;
import org.haw.vsp.restopoly.services.games.entities.Game;
import org.haw.vsp.restopoly.services.games.entities.Player;
import org.haw.vsp.restopoly.services.games.entities.State;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.exceptions.UnirestException;
import spark.Request;
import spark.Response;

public class Games extends Service {

	protected static final String NAME = "GamesService";

	public static final String DESCRIPTION = "A service for managing games";

	public static final String SERVICE_NAME = "games";

	public static final String SERVICE_URI = "/games";

	private static Map<String, Game> myGames = new HashMap<>();
	private static Gson myGson = new Gson();
	private static JsonParser myParser = new JsonParser();

	public static String getGames(Request request, Response response) {
		return myGson.toJson(myGames.values().stream()
				.map((game) -> Game.getJsonSummaryString(game))
				.toArray(String[]::new));
	}

	public static String postGame(Request request, Response response) {
		JsonObject json = myParser.parse(request.body()).getAsJsonObject();
		String id = getJsonAttribute(json, "id");
		String name = getJsonAttribute(json, "name");
		if(id == null || id == "") {
			id = name;
		}

		JsonObject servicesJson = json.get("services").getAsJsonObject();
		JsonElement componentsJsonElement = json.get("components");
		JsonObject componentsJson;
		if(componentsJsonElement == null) {
			componentsJson = new JsonObject();
		} else {
			componentsJson = json.get("components").getAsJsonObject();
		}
		

		Map<String, String> services = getMapFromJson(servicesJson);
		Map<String, String> components = getMapFromJson(componentsJson);

		Game newGame = new Game(SERVICE_URI + "/" + id, name, services, components);
		myGames.put(id, newGame);

		response.status(STATUS_CREATED);
		response.header("Location", newGame.getId());
		return Game.getJsonString(newGame);
	}

	public static String getGame(Request request, Response response) {
		String gameId = request.params(":gameId");

		String responseString = "";
		try {
			Game game = getGameById(gameId);
			responseString = Game.getUriJsonString(game);
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
			responseString = game.getState().toString();
		} catch (IllegalArgumentException e) {
			response.status(STATUS_NOT_FOUND);
		}
		return responseString;
	}
	
	public static String putStatus(Request request, Response response) {
		response.status(NOT_IMPLEMENTED);
		return NO_RESPONSE;
	}
	
	public static String getServices(Request request, Response response) {
		String gameId = request.params(":gameId");
		String responseString = "";
		try {
			Game game = getGameById(gameId);
			responseString = Game.getServicesAsJsonObject(game);
		} catch (IllegalArgumentException e) {
			response.status(STATUS_NOT_FOUND);
		}
		return responseString;
	}

	public static String getPlayers(Request request, Response response) { //TODO test this
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

	public static String postPlayer(Request request, Response response) { //TODO place players on the starting field  //TODO test this
		String gameId = request.params(":gameId");
		JsonObject json = myParser.parse(request.body()).getAsJsonObject();
		String id = getJsonAttribute(json, "id");
		String user = getJsonAttribute(json, "user");
		String pawn = getJsonAttribute(json, "pawn");
		String account = getJsonAttribute(json, "account");

		pawn = (pawn == "") ? getNewDefaultPawn() : pawn;
		account = (account == "") ? getNewAccount() : account;

		Player newPlayer = new Player(user, SERVICE + SERVICE_URI + "/" + id, pawn, account);


		String responseString = NO_RESPONSE;
		try {
			Game game = getGameById(gameId);
			game.addPlayer(newPlayer);
			response.status(STATUS_CREATED);
			response.header("Location",newPlayer.getId());
			responseString = Player.getJsonString(newPlayer);
		} catch (IllegalArgumentException e) {
			response.status(STATUS_NOT_FOUND);
		}
		return responseString;
	}

	public static String getPlayer(Request request, Response response) { //TODO test this
		String gameId = request.params(":gameId");
		String playerId = request.params(":playerId");

		String responseString = "";
		try {
			Game game = getGameById(gameId);
			Player player = game.getPlayer(SERVICE + SERVICE_URI + "/" + playerId);
			responseString = Player.getJsonString(player);
		} catch (IllegalArgumentException e) {
			response.status(STATUS_NOT_FOUND);
		}
		return responseString;
	}

	public static String getPlayerIsReady(Request request, Response response) { //TODO test this
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

	public static String putPlayerIsReady(Request request, Response response) { //TODO test this
		String gameId = request.params(":gameId");
		String playerId = request.params(":playerId");

		try {
			Game game = getGameById(gameId);
			game.setPlayerReady(playerId);
			if(game.getState() == State.REGISTRATION) { //start the game when everyone is ready
				boolean allPlayersReady = game.getPlayers().stream()
						.allMatch((player) -> player.isReady());
				if(allPlayersReady) {
					game.advanceState();
				}
			}
		} catch (IllegalArgumentException e) {
			response.status(STATUS_NOT_FOUND);
		}
		return NO_RESPONSE;
	}

	public static String getCurrentPlayer(Request request, Response response) { //TODO test this
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
	private static String getNewAccount() { //TODO implement this
		return null;
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

	private static Game getGameById(String gameId) throws IllegalArgumentException {
		Game game = myGames.get(gameId);
		if (game == null) {
			throw new IllegalArgumentException();
		}
		return game;
	}
	
	public static GamesRestClient getGamesRestClient() throws MissingServiceException {
		YellowPagesRestClient yellow = new YellowPagesRestClient(YellowPagesRestClient.HAW_YELLOW_PAGES_INTERNAL);
		GamesRestClient gamesRestClient = null;
		try {
			List<org.haw.vs.praktikum.gwln.yellowpages.Service> services = yellow.getServicesOfName(Games.SERVICE_NAME);
			if(services != null) {
				gamesRestClient = new GamesRestClient(services.get(0).getUri());
			} else {
				throw new MissingServiceException();
			}
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return gamesRestClient;
	}

	public static String getName() {
		return NAME + " " + GROUP_NAME;
	}
}
