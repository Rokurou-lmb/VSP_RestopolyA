
package org.haw.vsp.restopoly.services.broker;

import org.haw.vsp.restopoly.services.Service;

import spark.Request;
import spark.Response;

public class Broker extends Service{
	
	protected static final String NAME = "BrokerService";

	public static final String DESCRIPTION = "A service for managing estates";

	public static final String SERVICE_NAME = "broker";

	public static final String SERVICE_URI = "/broker";

	public static String getBrokers(Request request, Response response) {
		return "";
	}
	
	public static String postBroker(Request request, Response response) {
		return "";
	}
	
	public static String getBrokerByGameId(Request request, Response response) {
		return "";
	}
	
	public static String putBrokerByGameId(Request request, Response response) {
		return "";
	}
	
	public static String getPlacesByGameId(Request request, Response response) {
		return "";
	}
	
	public static String getPlaceByPlaceId(Request request, Response response) {
		return "";
	}
	
	public static String putPlaceByPlaceId(Request request, Response response) {
		return "";
	}
	
	public static String getOwnerByPlaceId(Request request, Response response) {
		return "";
	}
	
	public static String putOwnerByPlaceId(Request request, Response response) {
		return "";
	}
	
	public static String postOwnerByPlaceId(Request request, Response response) {
		return "";
	}
	
	public static String putHypoByPlaceId(Request request, Response response) {
		return "";
	}
	
	public static String deleteHypoByPlaceId(Request request, Response response) {
		return "";
	}
	
	public static String postVisitByPlaceId(Request request, Response response) {
		return "";
	}
	
	public static String getName() {
		return NAME + GROUP_NAME;
	}
}

