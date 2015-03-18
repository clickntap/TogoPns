package com.togocms.pns;

import com.caucho.hessian.client.HessianProxyFactory;
import com.togocms.pns.api.PushNotificationService;

public class PushNotification {
	private PushNotificationService service;
	private String apiKey;
	private String title;
	private String alert;
	private Long id;

	public PushNotification(String apiKey) throws Exception {
		this(apiKey, "http://pns.togocms.com/pns-service.app");
	}

	public PushNotification(String apiKey, String serverUrl) throws Exception {
		this.apiKey = apiKey;
		this.service = (PushNotificationService) new HessianProxyFactory().create(PushNotificationService.class, serverUrl);
	}

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

	public Long send() throws Exception {
		com.togocms.pns.api.PushNotification notification = new com.togocms.pns.api.PushNotification();
		notification.setTitle(getTitle());
		notification.setAlert(getAlert());
		id = service.sendBroadcastNotification(apiKey, notification);
		return id;
	}
}
