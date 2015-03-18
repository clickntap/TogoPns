package com.togocms.pns;

import java.util.List;

import com.clickntap.tool.types.Datetime;
import com.togocms.pns.api.PushNotification;
import com.togocms.pns.api.PushNotificationService;
import com.togocms.pns.bo.Channel;
import com.togocms.pns.bo.Message;

public class App extends com.clickntap.hub.App implements PushNotificationService {

	public List<Channel> getChannels() throws Exception {
		return getBOListByClass(Channel.class, "all");
	}

	public Long sendBroadcastNotification(String apiKey, PushNotification notification) throws Exception {
		Long id = createMessage(apiKey, notification);
		return id;
	}

	public Long sendNotification(String apiKey, PushNotification notification, Long userId) throws Exception {
		Long id = createMessage(apiKey, notification);
		return id;
	}

	public Long sendGroupNotification(String apiKey, PushNotification notification, List<Long> userIds) throws Exception {
		Long id = createMessage(apiKey, notification);
		return id;
	}

	private Number findChannelId(String apiKey) throws Exception {
		for (Channel channel : getChannels()) {
			if (apiKey.toLowerCase().toLowerCase().equals(channel.getApiKey())) {
				return channel.getId();
			}
		}
		return null;
	}

	private Long createMessage(String apiKey, PushNotification notification) throws Exception {
		Message message = new Message();
		message.setApp(this);
		message.setAlert(notification.getAlert());
		message.setChannelId(findChannelId(apiKey));
		message.setCreationTime(new Datetime());
		message.setLastModified(message.getCreationTime());
		message.setWorkflow(0);
		message.create();
		return message.getId().longValue();
	}

}
