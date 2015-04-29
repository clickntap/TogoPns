package com.togocms.pns;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.clickntap.tool.types.Datetime;
import com.togocms.pns.api.AndroidNotification;
import com.togocms.pns.api.IOSNotification;
import com.togocms.pns.api.PushNotification;
import com.togocms.pns.api.PushNotificationService;
import com.togocms.pns.bo.Channel;
import com.togocms.pns.bo.Device;
import com.togocms.pns.bo.Message;
import com.togocms.pns.bo.Push;

public class App extends com.clickntap.hub.App implements PushNotificationService {

	private Resource resourcesDir;

	public Resource getResourcesDir() {
		return resourcesDir;
	}

	public void setResourcesDir(Resource resourcesDir) {
		this.resourcesDir = resourcesDir;
	}

	public List<Channel> getChannels() throws Exception {
		return getBOListByClass(Channel.class, "all");
	}

	public Long sendBroadcastNotification(String apiKey, PushNotification notification) throws Exception {
		createMessage(apiKey, notification, 0);
		return notification.getId();
	}

	public Long sendNotification(String apiKey, PushNotification notification, Long userId) throws Exception {
		List<Long> userIds = new ArrayList<Long>();
		userIds.add(userId);
		return sendGroupNotification(apiKey, notification, userIds);
	}

	public Long sendGroupNotification(final String apiKey, final PushNotification notification, final List<Long> userIds) throws Exception {
		executeTransaction(new TransactionCallback() {
			public Object doInTransaction(TransactionStatus status) {
				try {
					createMessage(apiKey, notification, 1);
					try {
						for (Long userId : userIds) {
							Device device = new Device();
							device.setApp(App.this);
							device.setUserId(userId);
							device.setChannelId(notification.getChannelId());
							device.read("userId");
							device.read();
							if (device.getToken() != null) {
								Push push = new Push();
								push.setApp(App.this);
								push.setUserId(userId);
								push.setMessageId(notification.getId());
								push.setToken(device.getToken());
								push.setPlatform(device.getPlatform());
								push.create();
							}
						}
					} catch (Exception e) {
					}
				} catch (Exception e) {
					status.setRollbackOnly();
				}
				return null;
			}
		});
		return notification.getId();
	}

	public void init() throws Exception {
		exportKeyStores();
	}

	public void exportKeyStores() throws Exception {
		for (Channel channel : getChannels()) {
			if (channel.getKeyStore() != null) {
				File f = geyKeyStorePath(channel);
				FileUtils.writeByteArrayToFile(f, Base64.decodeBase64(channel.getKeyStore()));
			}
		}
	}

	private File geyKeyStorePath(Channel channel) throws IOException {
		return new File(getResourcesDir().getFile().getCanonicalPath() + "/" + channel.getId() + "_" + channel.getProduction() + ".p12");
	}

	public void sync() throws Exception {
		executeTransaction(new TransactionCallback() {
			public Object doInTransaction(TransactionStatus status) {
				try {
					Message message = new Message();
					message.setApp(App.this);
					message.read("next");
					if (message.getId() != null) {
						message.read();
						if (message.getWorkflow().intValue() == 0) {
							Channel channel = message.getChannel();
							List<String> addedTokens = new ArrayList<String>();
							for (Device device : channel.getDevices()) {
								if (!addedTokens.contains(device.getToken())) {
									addedTokens.add(device.getToken());
									Push item = new Push();
									item.setApp(App.this);
									item.setToken(device.getToken());
									item.setPlatform(device.getPlatform());
									item.setMessageId(message.getId());
									item.setCreationTime(new Datetime());
									item.setLastModified(item.getCreationTime());
									item.create();
								}
							}
							message.setLastModified(new Datetime());
							message.execute("queued");
						} else if (message.getWorkflow().intValue() == 1) {
							message.setAndroidSent(0);
							message.setAndroidFails(0);
							message.setIosSent(0);
							message.setIosFails(0);
							{ // android
								List<Push> androidDevices = message.getNextAndroidPushes();
								while (androidDevices.size() != 0) {
									AndroidNotification notification = new AndroidNotification();
									notification.setSecretKey(message.getChannel().getSecretKey());
									notification.setTitle(message.getTitle());
									notification.setAlert(message.getAlert());
									for (Push push : androidDevices) {
										notification.addToken(push.getToken());
										push.delete();
									}
									status.createSavepoint();
									int androidSent = androidDevices.size();
									int androidFails = 0;
									for (String badToken : notification.send()) {
										disableDevice(badToken);
										androidSent--;
										androidFails++;
									}
									message.setAndroidSent(message.getAndroidSent().intValue() + androidSent);
									message.setAndroidFails(message.getAndroidFails().intValue() + androidFails);
									androidDevices = message.getNextAndroidPushes();
								}
							}
							{ // apple
								List<Push> iosDevices = message.getNextIosPushes();
								while (iosDevices.size() != 0) {
									IOSNotification notification = new IOSNotification();
									notification.setProduction(message.getChannel().getProduction().intValue() == 1);
									notification.setKeyStorePath(geyKeyStorePath(message.getChannel()).getCanonicalPath());
									notification.setKeyStorePassword(message.getChannel().getKeyStorePassword());
									notification.setAlert(message.getAlert());
									for (Push push : iosDevices) {
										notification.addToken(push.getToken());
										push.delete();
									}
									status.createSavepoint();
									int iosSent = iosDevices.size();
									int iosFails = 0;
									for (String badToken : notification.send()) {
										disableDevice(badToken);
										iosSent--;
										iosFails++;
									}
									message.setIosSent(message.getIosSent().intValue() + iosSent);
									message.setIosFails(message.getIosFails().intValue() + iosFails);
									iosDevices = message.getNextIosPushes();
								}
							}
							message.setLastModified(new Datetime());
							message.execute("sent");
						}
					}
				} catch (Exception e) {
					status.setRollbackOnly();
				}
				return null;
			}

		});
	}

	private void disableDevice(String badToken) {
		try {
			Device device = new Device();
			device.setApp(App.this);
			device.setToken(badToken);
			device.execute("disable");
		} catch (Exception e) {
		}
	}

	private Number findChannelId(String apiKey) throws Exception {
		Channel channel = new Channel();
		channel.setApp(this);
		channel.setApiKey(apiKey);
		channel.read("apiKey");
		return channel.getId();
	}

	private void createMessage(String apiKey, PushNotification notification, int workflow) throws Exception {
		Message message = new Message();
		message.setApp(this);
		message.setTitle(notification.getTitle());
		message.setAlert(notification.getAlert());
		message.setChannelId(findChannelId(apiKey));
		message.setCreationTime(new Datetime());
		message.setLastModified(message.getCreationTime());
		message.setWorkflow(workflow);
		message.create();
		notification.setChannelId(message.getChannelId().longValue());
		notification.setId(message.getId().longValue());
	}

}
