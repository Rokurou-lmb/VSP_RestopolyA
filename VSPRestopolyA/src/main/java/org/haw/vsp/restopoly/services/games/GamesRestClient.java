package org.haw.vsp.restopoly.services.games;

import java.util.Collection;
import java.util.Map;

import org.haw.vsp.restopoly.services.games.entities.Game;
import org.haw.vsp.restopoly.services.games.entities.Player;
import org.haw.vsp.restopoly.services.games.entities.State;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class GamesRestClient {
	
	private final String myUri;
	private static JsonParser myParser = new JsonParser();
	
	public GamesRestClient(String uri) {
		myUri = uri;
	}
	
	public Collection<Game> getGames() {
		return null;
	}
	
	public void postGame(Game game) {
		
	}
	
	public Game getGame(String gameId) throws UnirestException {
		String responseString ="";
		JsonObject json = myParser.parse(Unirest.get(myUri).asString().toString()).getAsJsonObject();
		Game game = Game.fromUriJson(json);
		return game;
	}
	
	public State getStatus() {
		return null;
	}
	
	public void putState(State state) {
		
	}
	
	public Collection<Player> getPlayers(String gameId) {
		return null;
	}
	
	public String postPlayer(Player player) {
		return null;
	}
	
	public Player getPlayer(String gameId, String playerId) {
		return null;
	}
	
	public boolean getPlayerIsReady(String gameId, String playerId) {
		return false;
	}
	
	public void putPlayerIsReady(String gameId, String playerId) {
		
	}
	
	public Player getCurrentPlayer(String gameId) {
		return null;
	}

	public Map<String, String> getComponents(String componentsUri) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, String> getServices(String servicesUri) {
		// TODO Auto-generated method stub
		return null;
	}
}
