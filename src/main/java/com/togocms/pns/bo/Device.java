package com.togocms.pns.bo;

import com.clickntap.hub.BO;

public class Device extends BO {
	private String token;
	private Number platform;
	private Number userId;
	private Number channelId;

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

	public Number getUserId() {
		return userId;
	}

	public void setUserId(Number userId) {
		this.userId = userId;
	}

	public Number getChannelId() {
		return channelId;
	}

	public void setChannelId(Number channelId) {
		this.channelId = channelId;
	}

}
