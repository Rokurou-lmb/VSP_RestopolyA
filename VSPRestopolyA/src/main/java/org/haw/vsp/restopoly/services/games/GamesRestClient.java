package org.haw.vsp.restopoly.services.games;

import java.util.Collection;
import org.haw.vsp.restopoly.services.games.entities.Game;
import org.haw.vsp.restopoly.services.games.entities.Player;
import org.haw.vsp.restopoly.services.games.entities.State;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class GamesRestClient {
	
	private final String _uri;
	
	public GamesRestClient(String uri) {
		_uri = uri;
	}
	
	public Collection<Game> getGames() throws UnirestException {
		HttpResponse<JsonNode> jsonResponse = Unirest.get(_uri).asJson();
		
		return null;
	}
	
	public void postGame(Game game) throws UnirestException {
		HttpResponse<String> stringResponse = Unirest.post(_uri).body(Game.getJsonString(game)).asString();
	}
	
	public Game getGame(String gameId) throws UnirestException {
		HttpResponse<JsonNode> jsonResponse = Unirest.get(_uri + "/{id}").routeParam("id", gameId).asJson();
		return null;
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
}
