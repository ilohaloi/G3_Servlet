package com.outherutil.json;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import kotlin.Pair;

public class PairSerializer implements JsonSerializer<Pair<?, ?>>{

	@Override
	public JsonElement serialize(Pair<?, ?> src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
        jsonObject.add("first", context.serialize(src.getFirst()));
        jsonObject.add("second", context.serialize(src.getSecond()));
        return jsonObject;
	}

}
