package org.example;

import lombok.extern.java.Log;

@Log
public class Main
{
	public static void main(String[] args)
	{
		if (args.length == 0)
		{
			log.info("How to use: pass at least one parameter for the day of the Advent of Code plus the input file as the second parameter.");
			log.info("For example: java -jar 1 text.txt");
			return;
		}
		switch (args[0])
		{
			case "1" -> new One().run(args[1]);
			default -> log.info("Run with advent day as index followed by input file.");
		}
	}
}
