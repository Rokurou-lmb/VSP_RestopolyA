package org.haw.vsp.restopoly.services.brokers.entities;

import java.util.List;

public class Estate {
	
	/**
	 * Uri to the estate itself
	 */
	private String myId;
	
	/**
	 * Uri to the place on the board
	 */
	private String myPlaceUri;
	
	/**
	 * Uri to the player resource that owns this estate
	 */
	private String myOwner;
	
	/**
	 * Uri to the owner resource of the estate
	 */
	private String myOwnerUri;
	
	/**
	 * Value of the estate, i.e. for how much it may be bought or sold
	 */
	private Integer myValue;
	
	/**
	 * Rent at current level
	 */
	private List<Integer> myRent;
	
	/**
	 * Cost for house upgrade
	 */
	private List<Integer> myCost;
	
	/**
	 * Amount of houses set on the estate
	 */
	private Integer myHouses;
	
	/**
	 * Uri to the visit resource of this estate
	 */
	private String myVisitUri;
		
	/**
	 * Uri to the hypocredit resource of this estate"
	 */
	private String myHypocreditUri;
	
	public Estate(String placeId){
		myPlaceUri = placeId;
		//TODO: initialise the Estate object
	}

	public String getOwner() {
		return myOwner;
	}

	public void setOwner(String owner) {
		myOwner = owner;
	}

	public String getId() {
		return myId;
	}

	public void setId(String id) {
		myId = id;
	}
}
