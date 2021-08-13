package dev.huh.commonutils.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class FileReader {

	private final Logger log = LoggerFactory.getLogger(FileReader.class);

	public String readFileAsString(String filename) {
		String json = "";
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename)) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			json = bufferedReader.lines().collect(Collectors.joining());
		} catch (IOException e) {
			log.error("<-- Failed to read file {}", filename, e);
		}
		return json;
	}

}
