package com.outherutil.json;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.order.dto.WebOrderDetail;

import kotlin.Pair;

public interface JsonSerializerInterface {

	public default String createJsonKvObject(Map<String,String> data) {
		JsonObject job = new JsonObject();
		data.forEach(job::addProperty);
		return toJson(job);
	}

	public default String createJsonKvObject(String...data) {
		Map<String,String> respData= new LinkedHashMap<>();
		for (int i = 0; i < data.length; i+=2) {
				respData.put(data[i], data[i+1]);
		}
		JsonObject job = new JsonObject();
		respData.forEach(job::addProperty);
		return toJson(job);
	}
	public default String toJson(JsonObject json){
		Gson gson = new Gson();
		return gson.toJson(json);
	}
	public default <T>String toJson(List<T> list,boolean annotation){
		Gson gson = null;
		if(annotation)
			gson = new GsonBuilder()
			.setPrettyPrinting()
			.excludeFieldsWithoutExposeAnnotation()
			.setDateFormat("yyyy-MM-dd HH:mm:ss")
			.create();
		else
			gson = new Gson();

		return gson.toJson(list);
	}

	public default <T>String toJson(T data ,boolean annotation){

		Gson gson = null;
		if(annotation)
			gson = new GsonBuilder()
			.setPrettyPrinting()
			.excludeFieldsWithoutExposeAnnotation()
			.create();
		else
			gson = new Gson();

		return gson.toJson(data);
	}

	public default String toJson(WebOrderDetail data) {
		Gson gson = new GsonBuilder()
                .registerTypeAdapter(Pair.class, new PairSerializer())
                .registerTypeAdapter(Pair.class, new PairDeserializer())
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
		return gson.toJson(data);
	}
}
