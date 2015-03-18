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

	public Long sendBroadcastNotification(String channel, PushNotification notification) throws Exception {
		Long id = createMessage(channel, notification);
		return id;
	}

	public Long sendNotification(String channel, PushNotification notification, Long userId) throws Exception {
		Long id = createMessage(channel, notification);
		return id;
	}

	public Long sendGroupNotification(String channel, PushNotification notification, List<Long> userIds) throws Exception {
		Long id = createMessage(channel, notification);
		return id;
	}

	private Number findChannelId(String channelName) throws Exception {
		for (Channel channel : getChannels()) {
			if (channel.getName().toLowerCase().equals(channelName.toLowerCase())) {
				return channel.getId();
			}
		}
		return null;
	}

	private Long createMessage(String channel, PushNotification notification) throws Exception {
		Message message = new Message();
		message.setAlert(notification.getAlert());
		message.setChannelId(findChannelId(channel));
		message.setCreationDate(new Datetime());
		message.setLastModified(message.getCreationDate());
		message.setWorkflow(0);
		message.create();
		return message.getId().longValue();
	}

}
