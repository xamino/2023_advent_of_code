package org.example;

import lombok.extern.java.Log;
import java.io.IOException;

@Log
public class Main
{
	private static final String DEFAULT_DAY = "1";

	public static void main(String[] args) throws IOException
	{
		if (args.length == 0)
		{
			log.info("How to use: pass at least one parameter for the day of the Advent of Code plus the input file as the second parameter.");
			log.info("For example: java -jar 1 text.txt");
			return;
		}
		switch (args[0])
		{
			case "default" -> main(new String[] { DEFAULT_DAY });
			case "1" -> new One().run();
			default -> log.info("Run with advent day as index followed by input file.");
		}
	}
}
