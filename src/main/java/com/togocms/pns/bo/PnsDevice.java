package com.togocms.pns.bo;

import com.clickntap.hub.BO;

public class PnsDevice extends BO {
	public String token;
	public Long userId;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
