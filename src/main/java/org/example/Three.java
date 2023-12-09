package org.example;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
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
		var sumOfValidParts = candidates.stream().filter(c -> c.symbol != null)
			.map(PartCandidate::getNumber).reduce(0, Integer::sum);
		log.info("Sum of valid parts is " + sumOfValidParts);

		Map<String, List<Integer>> gearPartsBySymbolIndex = new HashMap<>();
		candidates.forEach(c -> {
			if (c.symbol == null || c.symbol != '*')
			{
				return;
			}
			gearPartsBySymbolIndex.putIfAbsent(c.symbolIndex, new ArrayList<>());
			gearPartsBySymbolIndex.get(c.symbolIndex).add(c.number);
		});
		log.info(gearPartsBySymbolIndex.toString());
		int sum = 0;
		for (Map.Entry<String, List<Integer>> gearCandidates : gearPartsBySymbolIndex.entrySet())
		{
			var list = gearCandidates.getValue();
			if (list.size() != 2)
			{
				continue;
			}
			sum += list.get(0) * list.get(1);
		}
		log.info("Sum of all gear ratios is " + sum);
	}

	@RequiredArgsConstructor
	private static class PartCandidate
	{
		@Getter
		private final int number;

		private final int row;

		private final int start;

		private final int end;

		private Character symbol;

		@Getter
		private String symbolIndex;

		private void findSymbol(String[] rows)
		{
			for (int i = Math.max(0, row - 1); i <= Math.min(rows.length - 1, row + 1); i++)
			{
				log.fine(number + ": checking row " + i);
				String bounded = rows[i].substring(getBoundsStart(), Math.min(rows[i].length(),
					end + 2));
				findAndSetSymbolFromRow(bounded, i);
			}
		}

		private int getBoundsStart()
		{
			return Math.max(0, start - 1);
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
				symbol = c;
				int charColumn = i + getBoundsStart();
				symbolIndex = rowIndex + ":" + charColumn;
				log.info(number + ": symbol " + c + " found at " + symbolIndex);
				return;
			}
			log.fine(number + ": no symbol found");
		}
	}
}
