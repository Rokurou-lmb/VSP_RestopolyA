package org.haw.vsp.restopoly.services;

import com.google.gson.JsonObject;

public abstract class Service {
	
	protected static final String GROUP_NAME = "42_1337_69";
	
	protected static final String NAME = "DefaultName";
	
	public static final String DESCRIPTION = "DefaultDescription";
	
	public static final String SERVICE_NAME = "default";
	
	public static final String SERVICE_URI = "/default";
	
	/**
	 * Value for service calls that return no body.
	 */
	public static final String NO_RESPONSE = "";
	
	public static final int STATUS_OK = 200;
	
	public static final int STATUS_CREATED = 201;
	
	public static final int STATUS_NOT_FOUND = 404;
	
	public static final int STATUS_CONFLICT = 409;
	
	public static final int INTERNAL_SERVER_ERROR = 500;
	


	/**
	 * Gets the attribute of the given identifier from {@code json}
	 * 
	 * @param json
	 * @param identifier
	 * @return The attribute, or {@code null} if none was found.
	 */
	protected static String getJsonAttribute(JsonObject json, String identifier) {
		return json.get(identifier).getAsString();
	}
	
	public static String getName() {
		return NAME + GROUP_NAME;
	}
}
