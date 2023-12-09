package org.example;

import lombok.ToString;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Log
public class Four implements Day
{
	private final Path file = ROOT_DIRECTORY.resolve("4.txt");

	@Override
	public void run() throws IOException
	{
		final String input = Files.readString(file);
		var totalPoints = Arrays.stream(input.split("\n"))
			.map(Game::new)
			.map(Game::getPoints)
			.reduce(0, Integer::sum);
		log.info("Sum of points is " + totalPoints);
	}

	@ToString
	private static class Game
	{
		private int cardNumber;

		private Set<Integer> winningNumbers = new HashSet<>();

		private Set<Integer> haveNumbers = new HashSet<>();

		private Game(String line)
		{
			var cardSplit = line.split(":");
			cardNumber = Integer.parseInt(cardSplit[0].replace("Card", "").trim());
			var numberSplit = cardSplit[1].split("\\|");
			winningNumbers = stringToNumberSet(numberSplit[0]);
			haveNumbers = stringToNumberSet(numberSplit[1]);
		}

		private Set<Integer> stringToNumberSet(final String input)
		{
			Set<Integer> numbers = new HashSet<>();
			for (String number : input.split(" "))
			{
				if (StringUtils.isBlank(number))
				{
					continue;
				}
				numbers.add(Integer.parseInt(number));
			}
			return numbers;
		}

		private int getPoints()
		{
			Set<Integer> intersection = new HashSet<>(haveNumbers);
			intersection.retainAll(winningNumbers);
			return (int)Math.pow(2, intersection.size() - 1d);
		}
	}
}
