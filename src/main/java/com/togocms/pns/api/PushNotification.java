package com.togocms.pns.api;

import java.io.Serializable;

public class PushNotification implements Serializable {
	private String alert;

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

}
