package com.togocms.pns.api;

import java.util.List;

public interface PushNotificationService {

	public Long sendBroadcastNotification(String apiKey, PushNotification pushNotification) throws Exception;

	public Long sendNotification(String apiKey, PushNotification pushNotification, Long userId) throws Exception;

	public Long sendGroupNotification(String apiKey, PushNotification pushNotification, List<Long> userIds) throws Exception;

}
