package org.example;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
public class Three implements Day
{
	private final Path file = ROOT_DIRECTORY.resolve("3.txt");

	private final Pattern findNumbers = Pattern.compile("(\\d+)");

	@Override
	public void run() throws IOException
	{
		final String input = Files.readString(file);
		final String[] lines = input.split("\n");
		Set<PartCandidate> candidates = new HashSet<>();

		for (int row = 0; row < lines.length; row++)
		{
			Matcher result = findNumbers.matcher(lines[row]);
			while (result.find())
			{
				int startIndex = result.start(1);
				int endIndex = result.end(1) - 1;
				candidates.add(new PartCandidate(Integer.parseInt(result.group(1)), row, startIndex, endIndex));
			}
		}

		var sumOfValidParts = candidates.stream()
			.filter(c -> c.valid(lines))
			.map(PartCandidate::getNumber)
			.reduce(0, Integer::sum);
		log.info("Sum of valid parts is " + sumOfValidParts);
	}

	@RequiredArgsConstructor
	private static class PartCandidate
	{
		@Getter
		private final int number;

		private final int row;

		private final int start;

		private final int end;

		private boolean valid(String[] rows)
		{
			return Set.of(-1, 0, 1).stream()
				.map(i -> i + row)
				.filter(i -> i >= 0 && i < rows.length)
				.map(i -> getBoundingString(rows[i]))
				.anyMatch(this::hasSymbol);
		}

		/**
		 * Gets the string from the given row  with the same length, plus the single bounding
		 * character, in an index safe way.
		 */
		private String getBoundingString(String row)
		{
			int subStart = Math.max(0, start - 1);
			int subEnd = Math.min(row.length(), end + 2);
			return row.substring(subStart, subEnd);
		}

		private boolean hasSymbol(String value)
		{
			for (char c : value.toCharArray())
			{
				if (Character.isDigit(c) || c == '.')
				{
					continue;
				}
				log.info("Symbol found: " + c);
				return true;
			}
			return false;
		}
	}
}
