package org.example;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
public class One implements Day
{
	private final Path file = ROOT_DIRECTORY.resolve("1.txt");

	final Pattern firstNumberAll = Pattern.compile("^.*?(\\d|one|two|three|four|five|six|seven|eight|nine)");

	final Pattern firstNumberDigit = Pattern.compile("^.*?(\\d)");

	final Pattern lastNumberAll = Pattern.compile(".*(\\d|one|two|three|four|five|six|seven|eight|nine)[a-z]*?$");

	final Pattern lastNumberDigit = Pattern.compile("(\\d)[a-z]*$");

	@Override
	public void run() throws IOException
	{
		final String input = Files.readString(file);

		int sumNumbers = 0;
		int sumText = 0;
		for (String line : input.split("\n"))
		{
			sumNumbers += getNumber(line, false);
			sumText += getNumber(line, true);
		}
		log.info("First answer is: " + sumNumbers);
		log.info("Second answer is: " + sumText);
	}

	private int getNumber(final String line, boolean includeText) throws IOException
	{
		String first;
		String last;
		if (includeText)
		{
			first = match(firstNumberAll, line);
			last = match(lastNumberAll, line);
		}
		else
		{
			first = match(firstNumberDigit, line);
			last = match(lastNumberDigit, line);
		}
		return convert(first + last);
	}

	private String match(final Pattern pattern, String line)
	{
		Matcher match = pattern.matcher(line);
		if (!match.find())
		{
			log.info("Found no match, returning empty");
			return "";
		}
		String found = match.group(1);
		switch (found)
		{
			case "one" -> found = "1";
			case "two" -> found = "2";
			case "three" -> found = "3";
			case "four" -> found = "4";
			case "five" -> found = "5";
			case "six" -> found = "6";
			case "seven" -> found = "7";
			case "eight" -> found = "8";
			case "nine" -> found = "9";
			default -> log.fine("No transformation required");
		}
		log.info("Found match <" + found + "> in <" + line + ">");
		return found;
	}

	private int convert(String input) throws IOException
	{
		if (StringUtils.isBlank(input))
		{
			log.fine("Received blank string, returning zero");
			return 0;
		}
		try
		{
			return Integer.parseInt(input);
		}
		catch (NumberFormatException e)
		{
			throw new IOException(e);
		}
	}
}
