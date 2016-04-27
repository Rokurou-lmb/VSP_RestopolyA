package org.haw.vsp.restopoly.services.boards;

import java.util.List;

import org.haw.vsp.restopoly.services.boards.entities.Board;
import org.haw.vsp.restopoly.services.games.Games;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;

public class Boards {
	private static Gson myGson = new Gson();
	
	private static List<Board> boards;
	
	//Games uri ?????
	//Games Entity ?????
	private static Games myGames;
	
	public static String getAllAktiveGames(Request request, Response response){
		myGames.getGames(request, response);
		
		return "";
	}

}
