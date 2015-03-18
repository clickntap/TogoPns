package com.togocms.pns;

import java.util.List;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.clickntap.tool.types.Datetime;
import com.togocms.pns.api.AndroidNotification;
import com.togocms.pns.api.PushNotification;
import com.togocms.pns.api.PushNotificationService;
import com.togocms.pns.bo.Channel;
import com.togocms.pns.bo.Device;
import com.togocms.pns.bo.Message;
import com.togocms.pns.bo.Push;

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

	public void sync() throws Exception {
		executeTransaction(new TransactionCallback() {
			public Object doInTransaction(TransactionStatus status) {
				Message message = new Message();
				try {
					message.setApp(App.this);
					message.read("next");
					if (message.getId() != null) {
						message.read();
						if (message.getWorkflow().intValue() == 0) {
							Channel channel = message.getChannel();
							for (Device device : channel.getDevices()) {
								Push item = new Push();
								item.setApp(App.this);
								item.setToken(device.getToken());
								item.setPlatform(device.getPlatform());
								item.setMessageId(message.getId());
								item.setCreationTime(new Datetime());
								item.setLastModified(item.getCreationTime());
								item.create();
							}
							message.setLastModified(new Datetime());
							message.execute("queued");
						} else if (message.getWorkflow().intValue() == 1) {
							List<Push> androidDevices = message.getNextAndroidPushes();
							if (androidDevices.size() != 0) {
								AndroidNotification notification = new AndroidNotification();
								notification.setSecretKey(message.getChannel().getSecretKey());
								notification.setTitle(message.getTitle());
								notification.setAlert(message.getAlert());
								for (Push push : androidDevices) {
									notification.addToken(push.getToken());
									push.delete();
								}
								for (String badToken : notification.send()) {
									Device device = new Device();
									device.setApp(App.this);
									device.setToken(badToken);
									device.read("token");
									device.execute("disable");
								}
							}
							message.setLastModified(new Datetime());
							message.execute("sent");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					status.setRollbackOnly();
				}
				return null;
			}
		});
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
		message.setTitle(notification.getTitle());
		message.setAlert(notification.getAlert());
		message.setChannelId(findChannelId(apiKey));
		message.setCreationTime(new Datetime());
		message.setLastModified(message.getCreationTime());
		message.setWorkflow(0);
		message.create();
		return message.getId().longValue();
	}

}
