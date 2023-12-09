package org.example;

import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Log
public class One implements Day
{
	private final Path file = ROOT_DIRECTORY.resolve("1.txt");

	@Override
	public void run() throws IOException
	{
		final String input = Files.readString(file);
		int sum = 0;

		for (String line : input.split("\n"))
		{
			Character first = null;
			Character last = null;
			for (char c : line.toCharArray())
			{
				if (!Character.isDigit(c))
				{
					continue;
				}
				if (first == null)
				{
					first = c;
				}
				last = c;
			}
			sum += convert(first + "" + last);
		}
		log.info("Answer is: " + sum);
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
