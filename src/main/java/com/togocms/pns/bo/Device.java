package com.togocms.pns.bo;

import com.clickntap.hub.BO;

public class Device extends BO {
	private String token;
	private Number platform;

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

}
