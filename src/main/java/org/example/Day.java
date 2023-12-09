package org.example;

import java.io.IOException;
import java.nio.file.Path;

public interface Day
{
	Path ROOT_DIRECTORY = Path.of("src/main/resources/files");

	void run() throws IOException;
}
