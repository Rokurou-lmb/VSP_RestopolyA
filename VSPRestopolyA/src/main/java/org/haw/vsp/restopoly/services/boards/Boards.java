package org.haw.vsp.restopoly.services.boards;

import java.util.List;

import org.haw.vsp.restopoly.services.boards.entities.Board;
import org.haw.vsp.restopoly.services.games.Games;
import org.haw.vsp.restopoly.services.games.entities.Game;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.JsonNode;

import spark.Request;
import spark.Response;

public class Boards {
	private static Gson myGson = new Gson();
	private static final JsonParser myParser = new JsonParser();
	private static List<Board> boards;

	/**
	 * Returns all necessary Request
	 */
	private static BoardManagerRestClient boardManager = new BoardManagerRestClient();
	

	public static String getAllAktiveBoards(Request request, Response response) {
			 return myGson.toJson(boards);
	}
	
	public static String postNewBoard(Request request, Response response) {
		
		JsonObject json = myParser.parse(request.body()).getAsJsonObject();
		String gameUri = json.get("game").getAsString();
		
		Board newBoard = new Board();
		newBoard.setGame(gameUri);
		
		boards.add(newBoard);
		response.status(201);
		
		return "";
	}
	

	public static String getBoardOfGame(Request request, Response response) {
		String gameId = request.params(":gameId");

		return null;
	}
	
	public static String putBoardOfGame(Request request, Response response) {
		String gameId = request.params(":gameId");

		return null;
	}

	public static String deleteBoardOfGame(Request request, Response response) {
		String gameId = request.params(":gameId");
		return null;
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
		String pawns = request.params(":pawnid");
		return null;
	}
	
	public static String deletePlayerPawns(Request request, Response response){
		String gameId = request.params(":gameId");
		String pawns = request.params(":pawnid");
		return null;
	}
	
	public static String getPlayerPawns(Request request, Response response){
		String gameId = request.params(":gameId");
		String pawns = request.params(":pawnid");
		return null;
	}
	
	public static String postMovePlayerPawns(Request request, Response response){
		String gameId = request.params(":gameId");
		String pawns = request.params(":pawnid");
		return null;
	}
	
	public static String getAllRollsOfPawn(Request request, Response response){
		String gameId = request.params(":gameId");
		String pawns = request.params(":pawnid");
		return null;
	}
	
	public static String postRollsOfPawn(Request request, Response response){
		String gameId = request.params(":gameId");
		String pawns = request.params(":pawnid");
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
}
