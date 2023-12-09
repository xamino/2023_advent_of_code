package org.example;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
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

		// Generate candidates
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

		// Calculate symbols
		candidates.forEach(c -> c.findSymbol(lines));

		// Calculate sum of all symbol parts
		var sumOfValidParts = candidates.stream()
			.filter(c -> c.symbol.isPresent())
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

		private Optional<Character> symbol = Optional.empty();

		private Optional<String> symbolIndex = Optional.empty();

		private void findSymbol(String[] rows)
		{
			for (int i = Math.max(0, row - 1); i <= Math.min(rows.length - 1, row + 1); i++)
			{
				log.fine(number + ": checking row " + i);
				String bounded = getBoundingString(rows[i]);
				findAndSetSymbolFromRow(bounded, i);
			}
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

		private void findAndSetSymbolFromRow(String row, int rowIndex)
		{
			for (int i = 0; i < row.toCharArray().length; i++)
			{
				char c = row.charAt(i);
				if (Character.isDigit(c) || c == '.')
				{
					continue;
				}
				symbol = Optional.of(c);
				int charColumn = i + start;
				symbolIndex = Optional.of(rowIndex + ":" + charColumn);
				log.info(number + ": symbol " + c + " found at " + symbolIndex.get());
				return;
			}
			log.info(number + ": no symbol found");
		}
	}
}
