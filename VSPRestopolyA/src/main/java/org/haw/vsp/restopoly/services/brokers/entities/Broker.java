package org.haw.vsp.restopoly.services.brokers.entities;

import java.util.List;

import org.haw.vsp.restopoly.services.brokers.Brokers;

public class Broker {

	private String myId;
	
	private String myGame;
	
	/**
	 * String - uri of the resource
	 */
	private List<String> myEstates; 
	
	public Broker(String gameId){
		myId = Brokers.SERVICE_URI +"/"+ gameId;
	}

	/**
	 * @return the Id of the Broker
	 */
	public String getId() {
		return myId;
	}

	public static String getUriJsonString(Broker broker) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<String> getEstates(){
		return myEstates;
	}



}
