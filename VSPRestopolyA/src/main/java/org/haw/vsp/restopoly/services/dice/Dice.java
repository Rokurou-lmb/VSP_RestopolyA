package org.haw.vsp.restopoly.services.dice;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.haw.vs.praktikum.gwln.praktikum1.b.Event;
import org.haw.vs.praktikum.gwln.praktikum1.b.EventManagerRestClient;
import org.haw.vs.praktikum.gwln.praktikum1.b.EventManagerWebService;
import org.haw.vs.praktikum.gwln.yellowpages.Service;
import org.haw.vs.praktikum.gwln.yellowpages.YellowPagesRestClient;
import org.json.JSONObject;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import spark.Request;
import spark.Response;

public class Dice {
	
	public static final String NAME = "DiceService";
	
	public static final String DESCRIPTION = "A service for rolling a dice";
	
	public static final String SERVICE_NAME = "dice";
	
	public static final String SERVICE_URI = "/dice";
	
	public static final String YELLOW_PAGE_ID = "4";
	
	public static Integer roll(Request request, Response response) {
		response.status(200);
		response.type("application/json");
		// could be uris
		String player = request.queryParams("player");
		String game = request.queryParams("game");
		int result = (Integer) ThreadLocalRandom.current().nextInt(1, 7);
		
		try {
			YellowPagesRestClient yellow = new YellowPagesRestClient(YellowPagesRestClient.HAW_YELLOW_PAGES_INTERNAL);
			List<Service> services = yellow.getServicesOfName("Event Manager Service 42_1337_69");
			Service service = null;
			for(Service s : services) {
				if("running".equals(s.getStatus())) {
					service = s;
					break;
				}
			}
			if(service != null) {
				EventManagerRestClient events = new EventManagerRestClient(service.getUri());
				events.postEvent(new Event(game, "Dice Roll", "Dice Event", player + " rolled a " + result, request.uri(), player, String.valueOf(System.currentTimeMillis())));
			} else {
				System.out.println("[WARNING] Es wurde kein Event-Service gefunden, das Event wird nicht übertragen!");
			}
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return result;
	}
}
