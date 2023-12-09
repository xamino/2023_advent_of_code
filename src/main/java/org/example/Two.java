package org.example;

import lombok.extern.java.Log;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Log
public class Two implements Day
{
	private final Path file = ROOT_DIRECTORY.resolve("2.txt");

	@Override
	public void run() throws IOException
	{
		final String input = Files.readString(file);
		log.info(input);
	}
}
