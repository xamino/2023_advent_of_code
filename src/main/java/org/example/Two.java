package org.example;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.java.Log;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Log
public class Two implements Day
{
	private final Path file = ROOT_DIRECTORY.resolve("2.txt");

	@Override
	public void run() throws IOException
	{
		final String input = Files.readString(file);
		log.info(input);

		var result = Arrays.stream(input.split("\n"))
			.map(Game::new)
			.filter(g -> g.isValid(12, 13, 14))
			.map(Game::getIndex)
			.reduce(0, Integer::sum);

		log.info("Sum of valid game indexes is <" + result + ">");

	}

	@ToString
	private static class Game
	{
		@Getter
		int index;

		int maxRed = 0;

		int maxGreen = 0;

		int maxBlue = 0;

		private Game(final String line)
		{
			var split = line.split(":");
			index = Integer.parseInt(split[0].split(" ")[1]);
			var games = split[1].split(";");
			for (String game : games)
			{
				var colors = game.split(",");
				for (String color : colors)
				{
					var colorComponents = color.trim().split(" ");
					switch (colorComponents[1])
					{
						case "red" ->
							maxRed = Math.max(maxRed, Integer.parseInt(colorComponents[0]));
						case "green" ->
							maxGreen = Math.max(maxGreen, Integer.parseInt(colorComponents[0]));
						case "blue" ->
							maxBlue = Math.max(maxBlue, Integer.parseInt(colorComponents[0]));
						default -> log.info("Unknown color! " + color);
					}
				}
			}
		}

		private boolean isValid(int red, int green, int blue)
		{
			return maxRed <= red && maxGreen <= green && maxBlue <= blue;
		}
	}
}
