package org.example;

import lombok.Getter;
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
			.filter(Game::isValid)
			.map(Game::getIndex)
			.reduce(0, Integer::sum);

		log.info("Sum of valid game indexes is <" + result + ">");

	}

	@Getter
	private static class Game
	{
		int index;

		private Game(final String line)
		{

		}

		private boolean isValid()
		{
			return false;
		}
	}
}
