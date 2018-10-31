package br.com.contaazul.bankslip.helper;

import static org.junit.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

import org.springframework.util.ResourceUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonHelper {

	private static Gson gson = new Gson();

	private static JsonObject getJsonFileAsJsonObject(String file, String type) {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(JsonHelper.class.getResourceAsStream("/json/" + type + "/" + file)));
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(br).getAsJsonObject();
		assertNotNull(json);
		return json;
	}

	public static String getRequestFileAsString(String file) {
		return getJsonFileAsJsonObject(file, "request").toString();
	}

	public static String getResponseFileAsString(String file) {
		return getJsonFileAsJsonObject(file, "response").toString();
	}

	public static void assertKeysLength(String json, int length) {
		JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
		assertThat(jsonObject.keySet().size()).isEqualTo(length);
	}

	public static <T> T parse(String json, Class<T> clazz) {
		return gson.fromJson(json, clazz);
	}
	
	public static String toJson(Object object) {
		return gson.toJson(object);
	}
	
	public static final String loadRequest(String filename) throws FileNotFoundException, IOException {
		return loadFile("classpath:json/request/" + filename);
	}

	public static final String loadResponse(String filename) throws FileNotFoundException, IOException {
		return loadFile("classpath:json/response/" + filename);
	}
	
	private static String loadFile(String filePath) throws FileNotFoundException, IOException {
		File file = ResourceUtils.getFile(filePath);
		return new String(Files.readAllBytes(file.toPath()));
	}

}
