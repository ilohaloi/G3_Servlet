package com.outherutil;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class WebMultipleQueryDto {

	@SerializedName("query")
	List<String>queryList;

	@SerializedName("value")
	List<String>valueList;
	public WebMultipleQueryDto() {
		super();
	}
	public WebMultipleQueryDto(List<String> queryList, List<String> valueList) {
		super();
		this.queryList = queryList;
		this.valueList = valueList;
	}
	public List<String> getQueryList() {
		return queryList;
	}
	public List<String> getValueList() {
		return valueList;
	}
	public void setQueryList(List<String> queryList) {
		this.queryList = queryList;
	}
	public void setValueList(List<String> valueList) {
		this.valueList = valueList;
	}


}
