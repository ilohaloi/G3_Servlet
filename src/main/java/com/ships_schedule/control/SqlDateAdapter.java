package com.ships_schedule.control;

import java.lang.reflect.Type;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class SqlDateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	//這邊是指反序列化時做哪些事
	public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		try {
			String dateStr = json.getAsString();
			return new Date(dateFormat.parse(dateStr).getTime());
		} catch (ParseException e) {
			throw new JsonParseException("Failed to parse Date: " + json.getAsString(), e);
		}
	}

	@Override
	//這邊是指序列化時做哪些事
	public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(dateFormat.format(src));
	}

}
