package org.haw.vsp.restopoly.services.boards.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.haw.vs.praktikum.gwln.yellowpages.YellowPagesRestClient;
import org.haw.vsp.restopoly.services.boards.Boards;
import org.haw.vsp.restopoly.services.brokers.BrokerRestClient;
import org.haw.vsp.restopoly.services.brokers.Brokers;

import com.google.gson.JsonObject;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Board {

	private String myId;

	/**
	 * Maps from pawnId(uri) to Pawn
	 */
	private Map<String, Pawn> myPawns;

	/**
	 * Maps from position(int) to the corresponding field
	 */
	private Map<Integer, Place> myFields;

	private String myBroker;

	private BrokerRestClient myBrokerClient;

	private static final YellowPagesRestClient yellow = new YellowPagesRestClient(
			YellowPagesRestClient.HAW_YELLOW_PAGES_INTERNAL);

	/**
	 * Maps from placeUri to Place
	 */
	private Map<String, Place> myPositions;

	public Board(String gameId) {
		try {
			myId = Boards.SERVICE_URI + "/" + gameId;
			myFields = new HashMap<>();
			myPositions = new HashMap<>();
			myBrokerClient = new BrokerRestClient(yellow.getServicesOfName(Brokers.getName()).get(0).getUri());

			myBroker = getNewBroker(gameId);
			for (int i = 0; i < 40; i++) {
				myFields.put(i, new Place(i, gameId, "DefaultName"));
			}
		} catch (UnirestException e) {
			e.printStackTrace();
		}

	}

	public void move(String pawnId, int distance) {
		Place place = myPositions.get(pawnId);
		int position = place.getPosition();
		int nextFieldPosition = (position + distance) % myPositions.size();
		Place newField = myFields.get(nextFieldPosition);
		//		place.removePawn(pawnId); FIXME
		//		newField.addPawn(pawnId);
	}

	public void addPawn(Pawn pawn) {
		myPawns.put(pawn.getId(), pawn);
	}

	public String getId() {
		return myId;
	}

	public Map<Integer, Place> getFields() {
		return myFields;
	}

	public Pawn getPawnById(String pawnId) {
		return myPawns.get(pawnId);
	}

	/**
	 * Returns the uri of the place resource at the given position on the board
	 * @param position 
	 * @return
	 */
	public String getPlaceByPosition(int position) {
		return myFields.get(position).getPlace();
	}

	/**
	 * Creates a new broker and returns its uri.
	 * @param gameId the game for which a broker should be created
	 * @return the uri of the newly created broker
	 * @throws UnirestException 
	 */
	private String getNewBroker(String gameId) {
		try {
			return myBrokerClient.getBrokerByGameId(gameId);
		} catch (UnirestException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getJsonString(Board board) {
		JsonObject json = new JsonObject();
		json.addProperty("id", board.getId());
		json.addProperty("fields", getJsonStringOfFields(board.getFields().values()));
		return json.toString();
	}

	private static String getJsonStringOfFields(Collection<Place> fields) {
		List<Place> fieldList = new ArrayList<>(fields);
		String[] fieldStrings = new String[fields.size()];
		for (int i = 0; i < fieldList.size(); i++) {
			Place field = fieldList.get(i);
			fieldStrings[i] = getJsonStringOfField(field);
		}
		return fieldStrings.toString();
	}

	private static String getJsonStringOfField(Place field) {
		JsonObject json = new JsonObject();
		json.addProperty("place", field.getPlace());
		//		json.addProperty("pawns", field.getPawns().toArray().toString());

		return json.toString();
	}

	private class Place {

		/**
		 * Int representing my position on the board
		 */
		private final int myPosition;
		private final String myName;
		private final String myUri;

		private Place(int position, String gameId, String name) {
			myUri = Boards.SERVICE_URI + "/" + gameId + "/places/" + position;
			myPosition = position;
			myName = name;
			BrokerRestClient.registerNewPlace(myUri);

		}

		private String getPlace() {
			return myUri;
		}

		private int getPosition() {
			return myPosition;
		}

		private String getName() {
			return myName;
		}
	}
}
