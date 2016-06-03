package org.haw.vsp.restopoly.services.boards;

import java.util.Map;

import org.haw.vsp.restopoly.services.Service;
import org.haw.vsp.restopoly.services.boards.entities.Board;
import org.haw.vsp.restopoly.services.boards.entities.Pawn;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import spark.Request;
import spark.Response;

public class Boards extends Service {

	protected static final String NAME = "BoardsService";

	public static final String DESCRIPTION = "A service for managing Boards";

	public static final String SERVICE_NAME = "boards";

	public static final String SERVICE_URI = "/boards";

	private static Gson myGson = new Gson();
	private static JsonParser myParser = new JsonParser();

	/**
	 * Maps a gameUri to its board
	 */
	private static Map<String, Board> myBoards;

	/**
	 * Returns all necessary Request
	 */
	private static BoardsRestClient boardManager = new BoardsRestClient();

	public static String getBoards(Request request, Response response) {
		return myGson.toJson(myBoards);
	}

	public static String postNewBoard(Request request, Response response) {
		JsonObject json = myParser.parse(request.body()).getAsJsonObject();
		String gameUri = json.get("game").getAsString();
		
		Board newBoard = new Board(gameUri);
		
		myBoards.put(gameUri, newBoard);
		response.status(STATUS_CREATED);
		response.header("Location", gameUri);
		
		return "";
	}

	public static String getBoardOfGame(Request request, Response response) {
		String gameId = request.params(":gameId");

		return Board.getJsonString(myBoards.get(gameId));
	}

	public static String putBoardOfGame(Request request, Response response) {
		String gameId = request.params(":gameId");
		return null;
	}

	public static String deleteBoardOfGame(Request request, Response response) {
		String gameId = request.params(":gameId");
		myBoards.remove(gameId);
		return "";
	}

	public static String getAllPlayerPositions(Request request, Response response) {
		String gameId = request.params(":gameId");
		return null;
	}

	public static String postNewPlayerPawns(Request request, Response response) {
		String gameId = request.params(":gameId");
		Board board = myBoards.get(gameId);
		JsonObject json = myParser.parse(request.body()).getAsJsonObject();
		String player = getJsonAttribute(json, "player");
		String place = getJsonAttribute(json, "place");
		place = (place == "") ? board.getPlaceByPosition(0) : place ;
		String position = getJsonAttribute(json, "position");
		position = (position == "") ? "0" : position ;
		
		Pawn newPawn = new Pawn("", player, place, Integer.valueOf(position), "", "");
		board.addPawn(newPawn);
		return Pawn.getJsonString(newPawn);
	}

	public static String putPlacePlayerPawns(Request request, Response response) {
		String gameId = request.params(":gameId");
		String pawnId = request.params(":pawnId");
		return null;
	}

	public static String deletePlayerPawns(Request request, Response response) {
		String gameId = request.params(":gameId");
		String pawnId = request.params(":pawnId");

		return null;
	}

	public static String getPlayerPawn(Request request, Response response) {
		String gameId = request.params(":gameId");
		String pawnId = request.params(":pawnId");
		Board board = myBoards.get(gameId);
		Pawn pawn = board.getPawnById(pawnId);
		return Pawn.getJsonString(pawn);
	}

	public static String postMovePlayerPawns(Request request, Response response) {
		String gameId = request.params(":gameId");
		String pawnId = request.params(":pawnId");
		JsonObject json = myParser.parse(request.body()).getAsJsonObject();
		int distance = Integer.valueOf(getJsonAttribute(json, ""));
		Board board = myBoards.get(gameId);
		board.move(pawnId, distance);

		return NO_RESPONSE;
	}

	public static String getAllRollsOfPawn(Request request, Response response) {
		String gameId = request.params(":gameId");
		String pawns = request.params(":pawnId");
		return null;
	}

	//TODO deplement
	public static String postRollsOfPawn(Request request, Response response) {
		String gameId = request.params(":gameId");
		String pawnId = request.params(":pawnId");
		return null;
	}

	public static String getFreeFields(Request request, Response response) {
		String gameId = request.params(":gameId");
		return null;
	}

	public static String getFieldFromBoard(Request request, Response response) {
		String gameId = request.params(":gameId");
		String place = request.params(":place");
		return null;
	}

	public static String putFieldOnBoard(Request request, Response response) {
		String gameId = request.params(":gameId");
		String place = request.params(":place");
		return null;
	}

	public static String getName() {
		return NAME + " " + GROUP_NAME;
	}
}
