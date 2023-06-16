package parkingSystem.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Config {
	// ---------------------------------------------------------------------------------------------------
	private Map<String, String> propertiesMap = new HashMap<>();

	// ---------------------------------------------------------------------------------------------------
	public String getPropertyValue(String key) {
		return this.propertiesMap.get(key);
	}

	// ---------------------------------------------------------------------------------------------------
	private BufferedReader openConfigFile(Path filePath) {
		File file = filePath.toFile();
		if (!file.isFile() || !file.getName().endsWith(".cfg"))
			throw new RuntimeException("Fatal Error: invalid file'" + filePath.getFileName() + "'");

		try {
			return new BufferedReader(new FileReader(new File(filePath.toString())));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Fatal Error: FileNotFound 'config.cfg'");
		}
	}

	// ---------------------------------------------------------------------------------------------------
	private String[] extractTokens(String line) {
		if (line.trim().length() == 0)
			return null;

		String[] tokens = line.split(":");

		for (int i = 0; i < tokens.length; i++)
			tokens[i] = tokens[i].trim();

		return tokens;
	}

	// ---------------------------------------------------------------------------------------------------
	private void validateTokens(String[] tokens) {
		String errorMessage = String.format("Error: %s is not a valid pair 'Key : value'", Arrays.toString(tokens));

		if (tokens.length != 2)
			throw new RuntimeException(errorMessage);

		boolean isValidTokens = ((tokens[0].length() > 0) && (tokens[1].length() > 0));
		if (!isValidTokens)
			throw new RuntimeException(errorMessage);
	}

	// ---------------------------------------------------------------------------------------------------
	public void addProperty(String[] tokens) {
		final String key = tokens[0];
		final String value = tokens[1];

		this.propertiesMap.put(key, value);
	}

	// ---------------------------------------------------------------------------------------------------
	public void readFromFile(String fileName) {
		readFromFile(System.getProperty("user.dir"), fileName);
	}

	// ---------------------------------------------------------------------------------------------------
	public void readFromFile(String directory, String fileName) {
		Path filePath = Path.of(directory, fileName);
		readFromFile(filePath);
	}

	// ---------------------------------------------------------------------------------------------------
	public void readFromFile(Path filePath) {
		try (BufferedReader reader = openConfigFile(filePath)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] tokens = extractTokens(line);
				if (tokens == null)
					continue;

				validateTokens(tokens);
				addProperty(tokens);
			}
		} catch (IOException e) {
			throw new RuntimeException("Fatal Error: Could not read 'config.cfg'");
		}
	}
}
