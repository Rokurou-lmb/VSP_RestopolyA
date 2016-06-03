package org.haw.vsp.restopoly.services.brokers;

import java.util.List;

import org.haw.vsp.restopoly.services.brokers.entities.Broker;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class BrokerRestClient { //TODO implement the BrokerRestClient
	
	private static JsonParser myParser = new JsonParser();
	private String myUri;
	
	public BrokerRestClient(String uri) {
		myUri = uri;
	}
	
	public static void registerNewPlace(String placeUri) {
		
	}

	
	/**
	 * @param gameId
	 * @return the uri of the Broker which is assigned to board of the game
	 * @throws UnirestException
	 */
	public String getBrokerByGameId(String gameId) throws UnirestException {
		JsonElement json = myParser.parse((Unirest.get(myUri + "/" + gameId).toString()));
				
		return json.getAsJsonObject().get("id").toString();
	}
	
	public String postNewBroker(String gameId) throws UnirestException {
		JsonObject json = new JsonObject();
		json.addProperty("game", gameId);
		String responseString ="";
		responseString = Unirest.post(myUri).body(json).asString().getHeaders().get("Location").get(0);
		
		return responseString;
	}
	
	public List<Broker> getAllBroker(){
		return null;
	}
	
}
