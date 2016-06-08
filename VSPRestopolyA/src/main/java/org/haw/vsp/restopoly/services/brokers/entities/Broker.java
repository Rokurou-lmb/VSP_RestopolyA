package org.haw.vsp.restopoly.services.brokers.entities;

import java.util.Collection;
import java.util.Map;
import org.haw.vsp.restopoly.services.brokers.Brokers;

public class Broker {
	
	private String myId;
	
	/**
	 * String - uri of the resource
	 */
	private Map<String, Estate> myEstates;
	
	public Broker(String gameId) {
		myId = Brokers.SERVICE_URI + "/" + gameId;
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
	
	public Collection<Estate> getEstates() {
		return myEstates.values();
	}
	
	/**
	 * Returns the Estate which is registered under the given placeId
	 * @param placeId of the Estate
	 * @return the Estate
	 */
	public Estate getEstateById(String placeId) {
		return myEstates.get(placeId);
	}
	
	public boolean isRegistered(String placeId) {
		return myEstates.containsKey(placeId);
	}
	
	public String registerPlace(String placeId) {
		Estate newEstate = new Estate(placeId);
		myEstates.put(placeId, newEstate);
		return newEstate.getId();
	}
}
