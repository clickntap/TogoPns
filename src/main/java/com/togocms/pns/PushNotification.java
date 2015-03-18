package com.togocms.pns;

import com.caucho.hessian.client.HessianProxyFactory;
import com.togocms.pns.api.PushNotificationService;

public class PushNotification {
	private PushNotificationService service;
	private String channel;

	public PushNotification(String channel) throws Exception {
		this("http://pns.togocms.com/pns-service.app", channel);
	}

	public PushNotification(String channel, String serverUrl) throws Exception {
		this.channel = channel;
		this.service = (PushNotificationService) new HessianProxyFactory().create(PushNotificationService.class, serverUrl);
	}

	public Long send() throws Exception {
		com.togocms.pns.api.PushNotification notification = new com.togocms.pns.api.PushNotification();
		return service.sendBroadcastNotification(channel, notification);
	}
}
