package com.togocms.pns.api;

import java.util.ArrayList;
import java.util.List;

import javapns.Push;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

public class IOSNotification extends AbstractNotification {

	public List<String> send() throws Exception {
		List<BasicDevice> devices = new ArrayList<BasicDevice>();
		for (String token : getTokens()) {
			devices.add(new BasicDevice(token));
		}
		PushNotificationPayload payload = PushNotificationPayload.complex();
		payload.addAlert(getAlert());
		List<PushedNotification> notifications = Push.payload(payload, getKeyStorePath(), getKeyStorePassword(), isProduction(), devices);
		List<String> badTokens = new ArrayList<String>();
		for (PushedNotification notification : notifications) {
			if (!notification.isSuccessful()) {
				badTokens.add(notification.getDevice().getToken());
			}
		}
		return badTokens;
	}

}
