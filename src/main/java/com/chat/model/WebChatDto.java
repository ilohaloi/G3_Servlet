package com.chat.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.google.gson.annotations.Expose;

public class WebChatDto implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -612204030237441015L;
	@Expose
	private Integer id;
	@Expose
	private String receiver;
	@Expose
	private String sender;
	@Expose
	private String content;
	
	private Timestamp timestamp;
	
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	
	
	public WebChatDto() {
		super();
	}

	public WebChatDto(String receiver, String sender, String content, Timestamp timestamp) {
		super();
		this.receiver = receiver;
		this.sender = sender;
		this.content = content;
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "WebChatDto [receiver=" + receiver + ", sender=" + sender + ", content=" + content + ", timestamp="
				+ timestamp + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
