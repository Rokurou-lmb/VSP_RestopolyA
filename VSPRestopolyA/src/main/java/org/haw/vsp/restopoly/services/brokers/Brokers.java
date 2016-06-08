package org.haw.vsp.restopoly.services.brokers;

import java.util.Map;
import org.haw.vsp.restopoly.services.Service;
import org.haw.vsp.restopoly.services.brokers.entities.Broker;
import org.haw.vsp.restopoly.services.brokers.entities.Estate;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import spark.Request;
import spark.Response;

public class Brokers extends Service{
	
	protected static final String NAME = "BrokerService";

	public static final String DESCRIPTION = "A service for managing estates";

	public static final String SERVICE_NAME = "broker";

	public static final String SERVICE_URI = "/broker";
	
	private static JsonParser myParser = new JsonParser();
	
	private static Gson myGson = new Gson();
	
	/**
	 * Maps a gameUri to its broker
	 */
	private static Map<String, Broker> myBrokers;

	public static String getBrokers(Request request, Response response) {
		return myGson.toJson(myBrokers.values().stream()
				.map((broker) -> broker.getId())
				.toArray(String[]::new));
	}
	
	public static String postBroker(Request request, Response response) {
		JsonObject json = myParser.parse(request.body()).getAsJsonObject();
		String gameUri = json.get("game").getAsString();
		
		Broker newBroker = new Broker(gameUri);
		myBrokers.put(gameUri, newBroker);
		response.status(STATUS_CREATED);
		response.header("Location", gameUri);
		
		return "";
	}
	
	public static String getBroker(Request request, Response response) {
		String gameId = request.params(":gameId");
		Broker broker = myBrokers.get(gameId);
		return Broker.getUriJsonString(broker);
	}
	
	public static String putBrokerByGameId(Request request, Response response) {
		return "";
	}
	
	
	public static String getAllAvailablePlaces(Request request , Response response){ //TODO: prüfen (estates müssen noch in gültige Jsons umgewandelt werden)
		String gameId = request.params(":gameId");
		Broker broker = myBrokers.get(gameId);
		return  myGson.toJson(broker.getEstates());
	}
	
	public static String putRegisterPlace(Request request , Response response){
		String gameId = request.params(":gameId");
		String placeId = request.params(":placeId");
		
		Broker broker = myBrokers.get(gameId);
		
		if(!broker.isRegistered(placeId)) {
			String uri = broker.registerPlace(placeId); //TODO: probably need to concatenate something to the uri
			response.status(STATUS_CREATED);
			response.header("Location", uri);
		}
		return "";
	}
	
	public static String postVisitEstate(Request request , Response response){ //TODO implement this
		String gameId = request.params(":gameId");
		String placeId = request.params(":placeId");
		JsonObject json = myParser.parse(request.body()).getAsJsonObject(); //TODO: the body might be a json or the playerId itself
		String playerId = json.get("id").getAsString();
		
		Broker broker = myBrokers.get(gameId);
		Estate estate = broker.getEstateById(placeId);
		
		//Check for rent
		//If applicable notify the /banks service
		return "";
	}
	
	public static String getOwner(Request request , Response response){
		String gameId = request.params(":gameId");
		String placeId = request.params(":placeId");
		
		Broker broker = myBrokers.get(gameId);
		Estate estate = broker.getEstateById(placeId);
		
		return estate.getOwner();
	}
	
	public static String postOwner(Request request , Response response){
		String gameId = request.params(":gameId");
		String placeId = request.params(":placeId");
		JsonObject json = myParser.parse(request.body()).getAsJsonObject(); //TODO: the body might be a json or the playerId itself
		String playerId = json.get("id").getAsString();
		
		Broker broker = myBrokers.get(gameId);
		Estate estate = broker.getEstateById(placeId);
		
		estate.setOwner(playerId); //TODO: check whether the player can actually buy this estate (not sold, ...)
		
		String placeUri = "";
		response.status(STATUS_OK);
		response.header("Location", placeUri);
		return "";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static String getPlacesByGameId(Request request, Response response) {
		return "";
	}
	
	public static String getPlaceByPlaceId(Request request, Response response) {
		return "";
	}
	
	public static String putPlaceByPlaceId(Request request, Response response) {
		return "";
	}
	
	public static String getOwnerByPlaceId(Request request, Response response) {
		return "";
	}
	
	public static String putOwnerByPlaceId(Request request, Response response) {
		return "";
	}
	
	public static String postOwnerByPlaceId(Request request, Response response) {
		return "";
	}
	
	public static String putHypoByPlaceId(Request request, Response response) {
		return "";
	}
	
	public static String deleteHypoByPlaceId(Request request, Response response) {
		return "";
	}
	
	public static String postVisitByPlaceId(Request request, Response response) {
		return "";
	}
	
	public static String getName() {
		return NAME + GROUP_NAME;
	}
}
