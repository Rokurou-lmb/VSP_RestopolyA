package org.haw.vsp.restopoly.services.boards.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.haw.vsp.restopoly.services.boards.Boards;

import com.google.gson.JsonObject;

public class Board {

	private String myId;
	
	private Map<Integer, Field> myFields;
	
	private Map<String, Field> myPositions;
	
	public Board(String gameId) {
		myId = Boards.SERVICE_URI + "/" + gameId;
		myFields = new HashMap<>();
		myPositions = new HashMap<>();
		for (int i = 0; i < 40; i++) {
			myFields.put(i, new Field(i, gameId));
		}
	}

	public void move(String pawnId, int distance) {
		Field field = myPositions.get(pawnId);
		int position = field.getPosition();
		int nextFieldPosition = (position + distance) % myPositions.size();
		Field newField = myFields.get(nextFieldPosition);
		field.removePawn(pawnId);
		newField.addPawn(pawnId);
	}

	public String getId() {
		return myId;
	}
	
	public Map<Integer, Field> getFields() {
		return myFields;
	}

	public static String getJsonString(Board board) {
		JsonObject json = new JsonObject();
		json.addProperty("id", board.getId());
		json.addProperty("fields", getJsonStringOfFields(board.getFields().values()));
		return json.getAsString();
	}
	
	private static String getJsonStringOfFields(Collection<Field> fields) {
		List<Field> fieldList = new ArrayList<>(fields);
		String[] fieldStrings = new String[fields.size()];
		for (int i = 0; i < fieldList.size(); i++) {
			Field field = fieldList.get(i);
			fieldStrings[i] = getJsonStringOfField(field); 
		}
		return fieldStrings.toString();
	}

	private static String getJsonStringOfField(Field field) {
		JsonObject json = new JsonObject();
		json.addProperty("place", field.getPlace());
		json.addProperty("pawns", field.getPawns().toArray().toString());
		
		return json.getAsString();
	}

	private class Field {
		private final int myPosition;
		private final String myPlace;
		private Set<String> myPawns;
		
		private Field(int position, String gameId) {
			myPlace = Boards.SERVICE_URI + "/" + gameId + "/places/" + position;
			myPosition = position;
			myPawns = new HashSet<>();
		}
		
		private String getPlace() {
			return myPlace;
		}
		
		public int getPosition() {
			return myPosition;
		}

		private Set<String> getPawns() {
			return myPawns;
		}
		
		private void addPawn(String pawn) {
			myPawns.add(pawn);
		}
		
		private void removePawn(String pawn) {
			myPawns.remove(pawn);
		}
	}
}
