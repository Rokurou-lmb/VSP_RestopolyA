package org.haw.vsp.restopoly;

import static spark.Spark.*;

import org.haw.vsp.restopoly.services.Dice;
import org.haw.vsp.restopoly.services.Users;

public class StartUp {
	public static void main(String[] args) {
		get("/hello", (request, response) -> "Hello World");
		get("/dice", Dice::roll);
		
		get("/users", Users::getUsers);
		post("/users", Users::postUser);
		get("/users/:id", Users::getUser);
		delete("/users/:id", Users::deleteUser);
		put("/users/:id", Users::putUser);
	}
}
