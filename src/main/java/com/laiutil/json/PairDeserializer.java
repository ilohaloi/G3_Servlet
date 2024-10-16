package com.laiutil.json;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.order.model.OrderDetailVo;

import kotlin.Pair;

class PairDeserializer implements JsonDeserializer<Pair<?, ?>> {

	@Override
	public Pair<?, ?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();
		Integer first = context.deserialize(jsonObject.get("first"), Integer.class);
		OrderDetailVo second = context.deserialize(jsonObject.get("second"), OrderDetailVo.class);
		return new Pair<>(first, second);
	}
}