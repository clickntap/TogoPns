package com.togocms.pns.bo;

import com.clickntap.hub.BO;
import com.clickntap.tool.types.Datetime;

public class Push extends BO {
	private Number userId;
	private Number messageId;
	private String token;
	private Number platform;
	private Datetime creationTime;
	private Datetime lastModified;

	public Number getUserId() {
		return userId;
	}

	public void setUserId(Number userId) {
		this.userId = userId;
	}

	public Number getMessageId() {
		return messageId;
	}

	public void setMessageId(Number messageId) {
		this.messageId = messageId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Number getPlatform() {
		return platform;
	}

	public void setPlatform(Number platform) {
		this.platform = platform;
	}

	public Datetime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Datetime creationTime) {
		this.creationTime = creationTime;
	}

	public Datetime getLastModified() {
		return lastModified;
	}

	public void setLastModified(Datetime lastModified) {
		this.lastModified = lastModified;
	}

}
