package org.haw.vsp.restopoly.services.boards;

import java.util.Map;

import org.haw.vsp.restopoly.services.MissingServiceException;
import org.haw.vsp.restopoly.services.Service;
import org.haw.vsp.restopoly.services.boards.entities.Board;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import spark.Request;
import spark.Response;

public class Boards extends Service{

	public static final String NAME = "BoardsService";

	public static final String DESCRIPTION = "A service for managing Boards";

	public static final String SERVICE_NAME = "boards" + GROUP_NAME;

	public static final String SERVICE_URI = "/boards";
	
	private static Gson myGson = new Gson();
	private static JsonParser myParser = new JsonParser();
	private static Map<String, Board> boards;

	/**
	 * Returns all necessary Request
	 */
	private static BoardManagerRestClient boardManager = new BoardManagerRestClient();
	

	public static String getBoards(Request request, Response response) {
			 return myGson.toJson(boards);
	}
	
	public static String postNewBoard(Request request, Response response) {
		JsonObject json = myParser.parse(request.body()).getAsJsonObject();
		String gameUri = json.get("game").getAsString();
		
		Board newBoard = new Board(getGameIdFromUri(gameUri));
		
		boards.put(gameUri, newBoard);
		response.status(STATUS_CREATED);
		response.header("Location", gameUri);
		
		return "";
	}
	

	public static String getBoardOfGame(Request request, Response response) throws MissingServiceException {
		String gameId = request.params(":gameId");
		
		return Board.getJsonString(boards.get(gameId));
	}
	
	public static String putBoardOfGame(Request request, Response response) {
		String gameId = request.params(":gameId");
		//TODO implement
		return null;
	}

	public static String deleteBoardOfGame(Request request, Response response) {
		String gameId = request.params(":gameId");
		boards.remove(gameId);
		return "";
	}
	
	
	public static String getAllPlayerPositions(Request request, Response response){
		String gameId = request.params(":gameId");
		return null;
	}
	
	public static String postNewPlayerPawns(Request request, Response response){
		String gameId = request.params(":gameId");
		return null;
	}
	
	public static String putPlacePlayerPawns(Request request, Response response){
		String gameId = request.params(":gameId");
		String pawnId = request.params(":pawnid");
		return null;
	}
	
	public static String deletePlayerPawns(Request request, Response response){
		String gameId = request.params(":gameId");
		String pawnId = request.params(":pawnid");
		
		return null;
	}
	
	//TODO implement
	public static String getPlayerPawns(Request request, Response response){
		String gameId = request.params(":gameId");
		String pawnId = request.params(":pawnid");
		return null;
	}
	
	public static String postMovePlayerPawns(Request request, Response response){
		String gameId = request.params(":gameId");
		String pawnId = request.params(":pawnid");
		JsonObject json = myParser.parse(request.body()).getAsJsonObject();
		int distance = Integer.valueOf(getJsonAttribute(json, ""));
		Board board = boards.get(gameId);
		board.move(pawnId, distance);
		
		return NO_RESPONSE;
	}
	
	public static String getAllRollsOfPawn(Request request, Response response){
		String gameId = request.params(":gameId");
		String pawns = request.params(":pawnid");
		return null;
	}
	//TODO implement
	public static String postRollsOfPawn(Request request, Response response){
		String gameId = request.params(":gameId");
		String pawnId = request.params(":pawnid");
		return null;
	}
	
	public static String getFreeFields(Request request, Response response){
		String gameId = request.params(":gameId");
		return null;
	}
	
	public static String getFieldFromBoard(Request request, Response response){
		String gameId = request.params(":gameId");
		String place = request.params(":place");
		return null;
	}
	
	public static String putFieldOnBoard(Request request, Response response){
		String gameId = request.params(":gameId");
		String place = request.params(":place");
		return null;
	}
	
	private static String getGameIdFromUri(String uri) {
		String[] subStrings = uri.split("/");
		return subStrings[subStrings.length-1];
	}
}
