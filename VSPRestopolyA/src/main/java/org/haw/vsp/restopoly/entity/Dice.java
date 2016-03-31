package org.haw.vsp.restopoly.entity;

import java.util.concurrent.ThreadLocalRandom;

import spark.Request;
import spark.Response;

public class Dice {
	
	
	public static Integer roll(Request request, Response response) { 
		response.status(200);
		response.type("application/json");
		return (Integer)ThreadLocalRandom.current().nextInt(1,  7);}
//	Function<Integer, Integer> roll1 = Dice::roll;
}
