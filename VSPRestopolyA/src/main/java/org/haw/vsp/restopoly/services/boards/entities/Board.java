package org.haw.vsp.restopoly.services.boards.entities;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private List<Field> myFields;

	private String myGameUri;
	
	public Board(String gameUri) {
		myGameUri = gameUri;
		
		myFields = new ArrayList<Board.Field>();
		for (int i = 0; i < 40; i++) {
			//TODO: Spielfeld anlegen
		}
	}

	private class Field {

	}

	public void setGame(String gameUri) {
		myGameUri = gameUri;

	}

	
	public String getGameUriOfBoard(){
		return myGameUri;
	}
}
