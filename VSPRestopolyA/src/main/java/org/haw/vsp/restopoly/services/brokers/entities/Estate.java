package org.haw.vsp.restopoly.services.brokers.entities;

import java.util.List;

public class Estate {

	
	/**
	 * the uri to the estate itself
	 */
	private String myId;
	
	/**
	 * uri to the place on the board
	 */
	private String myPlace;
	
	/**
	 * the uri to the owner resource of the estate
	 */
	private String myOwner;
	
	/**
	 * The value of the place, i.e. for how much it may be bought or sold
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
	 * amount of houses set on the estate
	 */
	private Integer myHouses;
	
	/**
	 * the uri to the visit resource
	 */
	private String myVisit;
		
	/**
	 * "the uri to the hypocredit of the estate"
	 */
	private String myHypocredit;
	
	public Estate(){
		
	}
	
}
