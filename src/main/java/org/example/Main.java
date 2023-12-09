package org.example;

import lombok.extern.java.Log;
import java.io.IOException;

@Log
public class Main
{
	private static final String DEFAULT_DAY = "2";

	public static void main(String[] args) throws IOException
	{
		if (args.length == 0)
		{
			log.info("How to use: pass at least one parameter for the day of the Advent of Code.");
			log.info("For example: java -jar 1");
			return;
		}
		switch (args[0])
		{
			case "default" -> main(new String[] { DEFAULT_DAY });
			case "1" -> new One().run();
			case "2" -> new Two().run();
			default -> log.info("Run with advent day as index.");
		}
	}
}
