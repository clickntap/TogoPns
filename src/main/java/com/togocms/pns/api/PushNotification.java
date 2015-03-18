package com.togocms.pns.api;

import java.io.Serializable;

public class PushNotification implements Serializable {
	private String title;
	private String alert;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

}
