package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {
	private FileUtils() {
	}
	
	public static String loadAsString(String file) {
		StringBuilder results = new StringBuilder();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String buffer = "";
			while ((buffer = reader.readLine()) != null) {
				results.append(buffer);
			}
			reader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return results.toString();
	}
}
