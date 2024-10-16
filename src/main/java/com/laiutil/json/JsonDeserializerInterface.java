package com.laiutil.json;

import java.io.BufferedReader;
import java.io.IOException;
import com.google.gson.Gson;

public interface JsonDeserializerInterface {
	public default <T> T readJsonFromBufferedReader(BufferedReader reader, Class<T> object){

		StringBuilder jsonBuilder = new StringBuilder();
		String line;
		try (BufferedReader br = reader){
			while ((line = br.readLine()) != null) {
				jsonBuilder.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String json = jsonBuilder.toString();
		Gson gson = new Gson();
		return gson.fromJson(json, object);
	}
	public default <T> T jsonToObject(String json, Class<T> object){
		Gson gson = new Gson();
		return gson.fromJson(json, object);
	}

}