package org.haw.vsp.restopoly.services.dice;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.haw.vs.praktikum.gwln.praktikum1.b.Event;
import org.haw.vs.praktikum.gwln.praktikum1.b.EventManagerRestClient;
import org.haw.vs.praktikum.gwln.yellowpages.YellowPagesRestClient;
import org.haw.vsp.restopoly.services.Service;

import com.mashape.unirest.http.exceptions.UnirestException;

import spark.Request;
import spark.Response;

public class Dice extends Service{

	public static final String NAME = "DiceService";

	public static final String DESCRIPTION = "A service for rolling a dice";

	public static final String SERVICE_NAME = "dice" + GROUP_NAME;

	public static final String SERVICE_URI = "/dice";

	public static Integer roll(Request request, Response response) {
		response.status(200);
		response.type("application/json");
		//uris
		String player = request.queryParams("player");
		String game = request.queryParams("game");
		int result = (Integer) ThreadLocalRandom.current().nextInt(1, 7);

		if (game != null) {
			createEvent(request, player, game, result);
		}
		return result;
	}

	private static void createEvent(Request request, String player, String game, int result) {
		try {
			YellowPagesRestClient yellow = new YellowPagesRestClient(YellowPagesRestClient.HAW_YELLOW_PAGES_INTERNAL);
			List<org.haw.vs.praktikum.gwln.yellowpages.Service> services = yellow.getServicesOfName("Event Manager Service 42_1337_69");
			org.haw.vs.praktikum.gwln.yellowpages.Service service = null;
			for(org.haw.vs.praktikum.gwln.yellowpages.Service s : services) {
				if("running".equals(s.getStatus())) {
					service = s;
					break;
				}
			}
			if(service != null) {
					EventManagerRestClient events = new EventManagerRestClient(service.getUri());
					events.postEvent(new Event(game, "Dice Roll", "Dice Event", player + " rolled a " + result, request.uri(), player, String.valueOf(System.currentTimeMillis())));
					System.out.println("[!!! SUCCESS] Event erfolgreich gemeldet!");
			} else {
				System.out.println("[!!! WARNING] Es wurde kein Event-Service gefunden, das Event wird nicht übertragen!");
			}
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
}
