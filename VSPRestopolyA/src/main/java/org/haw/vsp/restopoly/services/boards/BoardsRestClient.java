package org.haw.vsp.restopoly.services.boards;

import com.google.gson.JsonParser;

/**
 * bietet alle Service aufrufe an
 */
public class BoardsRestClient {
	
	private static JsonParser myParser = new JsonParser();
	private String myUri;
	
	public BoardsRestClient(String uri){
		myUri = uri;
	}
}
