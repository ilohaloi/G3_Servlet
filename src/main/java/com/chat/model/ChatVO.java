package com.chat.model;
import java.sql.Timestamp;

import com.google.gson.annotations.SerializedName;


public class ChatVO {

	private int memb_id;
	private int empo_id;
	private Timestamp conv_speaking_time;	
	private String conv_content;
	
	public int getMembId() {
		return memb_id;
	}

	public void setMembId(int memb_id) {
		this.memb_id = memb_id;
	}
	
	public int getEmpoId() {
		return empo_id;
	}
	
	public void setEmpoId(int empo_id) {
	    this.empo_id = empo_id;
	}
	
	public Timestamp getConvSpeakingTime() {
		return conv_speaking_time;
	}
	
	public void setConvSpeakingTime(Timestamp conv_speaking_time) {
		this.conv_speaking_time = conv_speaking_time;
	}
	
	public String getConvContent() {
		return conv_content;
	}
	
	public void setConvContent(String conv_content) {
		this.conv_content = conv_content;
	}

	@Override
	public String toString() {
		return "ChatVO [memb_id=" + memb_id + ", empo_id=" + empo_id + ", conv_speaking_time=" + conv_speaking_time
				+ ", conv_content=" + conv_content + "]";
	}
	
	
}
	


