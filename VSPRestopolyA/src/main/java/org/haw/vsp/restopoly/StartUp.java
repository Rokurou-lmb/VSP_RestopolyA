package org.haw.vsp.restopoly;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import java.util.List;
import org.haw.vs.praktikum.gwln.yellowpages.Service;
import org.haw.vs.praktikum.gwln.yellowpages.YellowPagesRestClient;
import org.haw.vsp.restopoly.services.boards.Boards;
import org.haw.vsp.restopoly.services.dice.Dice;
import org.haw.vsp.restopoly.services.games.Games;
import org.haw.vsp.restopoly.services.users.Users;
import com.mashape.unirest.http.exceptions.UnirestException;

public class StartUp {
	
//	private static final String YELLOW_PAGES = "http://172.18.0.5:4567/services";
//	
//	private static final String SERVICE_URI = "http://abl459-services:4567";
	
	private static final YellowPagesRestClient YELLOW_PAGES_CLIENT = new YellowPagesRestClient(YellowPagesRestClient.HAW_YELLOW_PAGES_INTERNAL);
	
	public static void main(String[] args) {

		// Hello World
		get("/hello", (request, response) -> "Hello World");
		
		// Dice-Service
		get("/dice", Dice::roll);
		registerService(Dice.getName(), Dice.DESCRIPTION, Dice.SERVICE_NAME, Dice.SERVICE_URI);
		
		// Users-Service
		userService();
		
		// Games-Service
		gameService();
		
		// Board-Service
		boardService();
		
	}
	
	private static void userService() {
		get("/users", Users::getUsers);
		post("/users", Users::postUser);
		get("/users/:id", Users::getUser);
		delete("/users/:id", Users::deleteUser);
		put("/users/:id", Users::putUser);
		
		registerService(Users.getName(), Users.DESCRIPTION, Users.SERVICE_NAME, Users.SERVICE_URI);
	}
	
	private static void gameService() {
		get("/games", Games::getGames);
		post("/games", Games::postGame);
		get("/games/:gameId", Games::getGame);
		get("/games/:gameId/status", Games::getStatus);
		put("/games/:gameId/status", Games::putStatus);
		get("/games/:gameId/players", Games::getPlayers);
		post("/games/:gameId/players", Games::postPlayer);
		get("/games/:gameId/players/:playerId", Games::getPlayer);
		put("/games/:gameId/players/:playerId/ready", Games::putPlayerIsReady);
		get("/games/:gameId/players/:playerId/ready", Games::getPlayerIsReady);
		get("/games/:gameId/players/current", Games::getCurrentPlayer);
		
		registerService(Games.getName(), Games.DESCRIPTION, Games.SERVICE_NAME, Games.SERVICE_URI);
	}
	
	private static void boardService() {
		get("/boards", Boards::getBoards);
		post("/boards", Boards::postNewBoard);
		get("/boards/:gameId", Boards::getBoardOfGame);
		
		registerService(Boards.getName(), Boards.DESCRIPTION, Boards.SERVICE_NAME, Boards.SERVICE_URI);
	}
	
	private static void registerService(String name, String description, String serviceName, String uri) {
		try {
			Service service = new Service(name, serviceName, uri, description);
			Service lastService = null;
			lastService = removeOldServices(name);
			
			if(lastService == null) {
				YELLOW_PAGES_CLIENT.postService(service);
			} else {
				YELLOW_PAGES_CLIENT.putService(lastService.getId(), service);
			}
		} catch(UnirestException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Removes all services registered under the given name except for one and returns the last one, if atleast one exists.
	 * Otherwise returns null.
	 * @param serviceName name by which services were registered
	 * @return last remaining service registered under the given name or null if none existed
	 * @throws UnirestException
	 */
	private static org.haw.vs.praktikum.gwln.yellowpages.Service removeOldServices(String serviceName) throws UnirestException {
		List<org.haw.vs.praktikum.gwln.yellowpages.Service> servicesByName = YELLOW_PAGES_CLIENT.getServicesOfName(serviceName);
		org.haw.vs.praktikum.gwln.yellowpages.Service lastService = null;
		for(int i = 0; i < servicesByName.size(); i++) {
			if(i == (servicesByName.size() - 1)) {
				lastService = servicesByName.get(i);
			} else {
				YELLOW_PAGES_CLIENT.deleteService(servicesByName.get(i).getId());
			}
		}
		return lastService;
	}
}
