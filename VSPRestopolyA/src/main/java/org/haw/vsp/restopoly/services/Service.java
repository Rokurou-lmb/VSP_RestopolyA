package org.haw.vsp.restopoly.services;

public abstract class Service {
	
	public static final String GROUP_NAME = "42_1337_69";
	
	public static final String NAME = "DefaultName";
	
	public static final String DESCRIPTION = "DefaultDescription";
	
	public static final String SERVICE_NAME = "default";
	
	public static final String SERVICE_URI = "/default";
	
	/**
	 * Value for service calls the return no body.
	 */
	public static final String NO_RESPONSE = "";
	
	public static final int STATUS_OK = 200;
	
	public static final int STATUS_CREATED = 201;
	
	public static final int STATUS_NOT_FOUND = 404;
	
	public static final int STATUS_CONFLICT = 409;
	
	
}
