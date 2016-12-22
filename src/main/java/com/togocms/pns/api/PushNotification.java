package com.togocms.pns.api;

import java.io.Serializable;

public class PushNotification implements Serializable {
	private String title;
	private String alert;
	private Long id;
	private Long channelId;

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

	public String getMessage() {
		return getAlert();
	}

	public void setMessage(String message) {
		setAlert(message);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

}
