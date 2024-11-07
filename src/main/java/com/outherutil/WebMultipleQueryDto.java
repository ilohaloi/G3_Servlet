package com.outherutil;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class WebMultipleQueryDto {

	@SerializedName("query")
	List<String>query;
	@SerializedName("value")
	List<String>value;
	public List<String> getQuery() {
		return query;
	}
	public List<String> getValue() {
		return value;
	}
	public void setQuery(List<String> query) {
		this.query = query;
	}
	public void setValue(List<String> value) {
		this.value = value;
	}
	public WebMultipleQueryDto(List<String> query, List<String> value) {
		super();
		this.query = query;
		this.value = value;
	}
	public WebMultipleQueryDto() {
		super();
	}
}
