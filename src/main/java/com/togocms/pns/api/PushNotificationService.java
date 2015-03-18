package com.togocms.pns.api;

import java.util.List;

public interface PushNotificationService {

	public Long sendBroadcastNotification(String channel, PushNotification pushNotification) throws Exception;

	public Long sendNotification(String channel, PushNotification pushNotification, Long userId) throws Exception;

	public Long sendGroupNotification(String channel, PushNotification pushNotification, List<Long> userIds) throws Exception;

}
