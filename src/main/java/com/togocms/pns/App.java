package com.togocms.pns;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.clickntap.tool.types.Datetime;
import com.clickntap.utils.StringUtils;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.togocms.pns.api.PushNotification;
import com.togocms.pns.api.PushNotificationService;
import com.togocms.pns.bo.PnsChannel;
import com.togocms.pns.bo.PnsDevice;
import com.togocms.pns.bo.PnsMessage;
import com.togocms.pns.firebase.Firebase;
import com.togocms.pns.firebase.FirebaseResponse;

public class App extends com.clickntap.hub.App implements PushNotificationService {

	public Long sendBroadcastNotification(String apiKey, PushNotification pushNotification) throws Exception {
		return sendGroupNotification(apiKey, pushNotification, null);
	}

	public Long sendNotification(String apiKey, PushNotification pushNotification, Long userId) throws Exception {
		List<Long> ids = new ArrayList<Long>();
		ids.add(userId);
		return sendGroupNotification(apiKey, pushNotification, ids);
	}

	public Long sendGroupNotification(String apiKey, PushNotification pushNotification, List<Long> userIds) throws Exception {
		PnsChannel apiChannel = getApiChannel(apiKey);
		if (apiChannel != null) {
			PnsMessage message = new PnsMessage();
			message.setApp(this);
			message.setTitle(pushNotification.getTitle());
			message.setMessage(pushNotification.getMessage());
			message.setChannelId(apiChannel.getId());
			if (userIds != null) {
				message.setTargetIds(StringUtils.arrayToCommaDelimitedString(userIds.toArray()));
			}
			return message.create().longValue();
		}
		return 0L;
	}

	private PnsChannel getApiChannel(String apiKey) throws Exception {
		PnsChannel apiChannel = null;
		for (PnsChannel channel : getChannels()) {
			if (channel.getApiKey().equals(apiKey)) {
				apiChannel = channel;
				break;
			}
		}
		return apiChannel;
	}

	public List<PnsChannel> getChannels() throws Exception {
		return getBOListByClass(PnsChannel.class, "all");
	}

	public static void main(String args[]) throws Exception {
		FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext("etc/pns.xml");
		App app = (App) ctx.getBean("app");
		PushNotification notification = new PushNotification();
		notification.setTitle("App");
		notification.setMessage("Messaggio di test " + new Datetime());
		app.sendBroadcastNotification("1d9235ef65cad7238c34251635766b01", notification);
		app.sendNextMessage();
		ctx.close();
	}

	public void sync() throws Exception {
		sendNextMessage();
	}

	public void sendNextMessage() throws Exception {
		PnsMessage message = getBO(PnsMessage.class, "next");
		if (message != null) {
			message.read();
			PnsChannel channel = message.getChannel();
			{//android
				int androidSent = 0;
				int androidFails = 0;
				Firebase firebase = new Firebase(channel.getAndroidSecretKey());
				Sender sender = new Sender(channel.getAndroidSecretKey());
				for (PnsDevice device : channel.getAndroidDevices()) {
					try {
						if ("firebase".equals(channel.getAndroidType())) {
							FirebaseResponse response = firebase.sendMessage(device.getToken(), message.getTitle(), message.getMessage(), message.getSound());
							if (response.getJson().getInt("success") == 1) {
								androidSent++;
							} else {
								androidFails++;
								device.delete();
							}
						} else {
							JSONObject payload = new JSONObject();
							payload.put("title", message.getTitle());
							payload.put("message", message.getMessage());
							Message gcmMessage = new Message.Builder().addData("TogoMessage", message.getMessage()).addData("TogoInfo", payload.toString()).build();
							Result result = sender.send(gcmMessage, device.getToken(), 0);
							if (result.getMessageId() != null) {
								androidSent++;
							} else {
								androidFails++;
								device.delete();
							}
						}
					} catch (Exception e) {
						androidFails++;
					}
				}
				message.setAndroidSent(androidSent);
				message.setAndroidFails(androidFails);
			}
			{//apple
			}
			message.execute("done");
		}
	}
	/*
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
	
		public Long sendBroadcastNotification(String apiKey, PushNotification pushNotification) throws Exception {
			return null;
		}
	
		public Long sendNotification(String apiKey, PushNotification pushNotification, Long userId) throws Exception {
			return null;
		}
	
		public Long sendGroupNotification(String apiKey, PushNotification pushNotification, List<Long> userIds) throws Exception {
			return null;
		}
		//
		//	public Long sendBroadcastNotification(String apiKey, PushNotification notification) throws Exception {
		//		createMessage(apiKey, notification, 0);
		//		return notification.getId();
		//	}
		//
		//	public Long sendNotification(String apiKey, PushNotification notification, Long userId) throws Exception {
		//		List<Long> userIds = new ArrayList<Long>();
		//		userIds.add(userId);
		//		return sendGroupNotification(apiKey, notification, userIds);
		//	}
		//
		//	public Long sendGroupNotification(final String apiKey, final PushNotification notification, final List<Long> userIds) throws Exception {
		//		executeTransaction(new TransactionCallback() {
		//			public Object doInTransaction(TransactionStatus status) {
		//				try {
		//					createMessage(apiKey, notification, 1);
		//					try {
		//						for (Long userId : userIds) {
		//							Device device = new Device();
		//							device.setApp(App.this);
		//							device.setUserId(userId);
		//							device.setChannelId(notification.getChannelId());
		//							device.read("userId");
		//							device.read();
		//							if (device.getToken() != null) {
		//								Push push = new Push();
		//								push.setApp(App.this);
		//								push.setUserId(userId);
		//								push.setMessageId(notification.getId());
		//								push.setToken(device.getToken());
		//								push.setPlatform(device.getPlatform());
		//								push.create();
		//							}
		//						}
		//					} catch (Exception e) {
		//					}
		//				} catch (Exception e) {
		//					status.setRollbackOnly();
		//				}
		//				return null;
		//			}
		//		});
		//		return notification.getId();
		//	}
		//
		//	public void init() throws Exception {
		//		exportKeyStores();
		//	}
		//
		//	public void exportKeyStores() throws Exception {
		//		for (Channel channel : getChannels()) {
		//			if (channel.getKeyStore() != null) {
		//				File f = geyKeyStorePath(channel);
		//				FileUtils.writeByteArrayToFile(f, Base64.decodeBase64(channel.getKeyStore()));
		//			}
		//		}
		//	}
		//
		//	private File geyKeyStorePath(Channel channel) throws IOException {
		//		return new File(getResourcesDir().getFile().getCanonicalPath() + "/" + channel.getId() + "_" + channel.getProduction() + ".p12");
		//	}
		//
		//	public void sync() throws Exception {
		//		executeTransaction(new TransactionCallback() {
		//			public Object doInTransaction(TransactionStatus status) {
		//				try {
		//					Message message = new Message();
		//					message.setApp(App.this);
		//					message.read("next");
		//					if (message.getId() != null) {
		//						message.read();
		//						if (message.getWorkflow().intValue() == 0) {
		//							Channel channel = message.getChannel();
		//							List<String> addedTokens = new ArrayList<String>();
		//							for (Device device : channel.getDevices()) {
		//								if (!addedTokens.contains(device.getToken())) {
		//									addedTokens.add(device.getToken());
		//									Push item = new Push();
		//									item.setApp(App.this);
		//									item.setToken(device.getToken());
		//									item.setPlatform(device.getPlatform());
		//									item.setMessageId(message.getId());
		//									item.setCreationTime(new Datetime());
		//									item.setLastModified(item.getCreationTime());
		//									item.create();
		//								}
		//							}
		//							message.setLastModified(new Datetime());
		//							message.execute("queued");
		//						} else if (message.getWorkflow().intValue() == 1) {
		//							message.setAndroidSent(0);
		//							message.setAndroidFails(0);
		//							message.setIosSent(0);
		//							message.setIosFails(0);
		//							{ // android
		//								List<Push> androidDevices = message.getNextAndroidPushes();
		//								while (androidDevices.size() != 0) {
		//									AndroidNotification notification = new AndroidNotification();
		//									notification.setSecretKey(message.getChannel().getSecretKey());
		//									notification.setTitle(message.getTitle());
		//									notification.setAlert(message.getAlert());
		//									for (Push push : androidDevices) {
		//										notification.addToken(push.getToken());
		//										push.delete();
		//									}
		//									status.createSavepoint();
		//									int androidSent = androidDevices.size();
		//									int androidFails = 0;
		//									for (String badToken : notification.send()) {
		//										disableDevice(badToken);
		//										androidSent--;
		//										androidFails++;
		//									}
		//									message.setAndroidSent(message.getAndroidSent().intValue() + androidSent);
		//									message.setAndroidFails(message.getAndroidFails().intValue() + androidFails);
		//									androidDevices = message.getNextAndroidPushes();
		//								}
		//							}
		//							{ // apple
		//								List<Push> iosDevices = message.getNextIosPushes();
		//								while (iosDevices.size() != 0) {
		//									IOSNotification notification = new IOSNotification();
		//									notification.setProduction(message.getChannel().getProduction().intValue() == 1);
		//									notification.setKeyStorePath(geyKeyStorePath(message.getChannel()).getCanonicalPath());
		//									notification.setKeyStorePassword(message.getChannel().getKeyStorePassword());
		//									notification.setAlert(message.getAlert());
		//									for (Push push : iosDevices) {
		//										notification.addToken(push.getToken());
		//										push.delete();
		//									}
		//									status.createSavepoint();
		//									int iosSent = iosDevices.size();
		//									int iosFails = 0;
		//									for (String badToken : notification.send()) {
		//										disableDevice(badToken);
		//										iosSent--;
		//										iosFails++;
		//									}
		//									message.setIosSent(message.getIosSent().intValue() + iosSent);
		//									message.setIosFails(message.getIosFails().intValue() + iosFails);
		//									iosDevices = message.getNextIosPushes();
		//								}
		//							}
		//							message.setLastModified(new Datetime());
		//							message.execute("sent");
		//						}
		//					}
		//				} catch (Exception e) {
		//					status.setRollbackOnly();
		//				}
		//				return null;
		//			}
		//
		//		});
		//	}
		//
		//	private void disableDevice(String badToken) {
		//		try {
		//			Device device = new Device();
		//			device.setApp(App.this);
		//			device.setToken(badToken);
		//			device.execute("disable");
		//		} catch (Exception e) {
		//		}
		//	}
		//
		//	private Number findChannelId(String apiKey) throws Exception {
		//		Channel channel = new Channel();
		//		channel.setApp(this);
		//		channel.setApiKey(apiKey);
		//		channel.read("apiKey");
		//		return channel.getId();
		//	}
		//
		//	private void createMessage(String apiKey, PushNotification notification, int workflow) throws Exception {
		//		Message message = new Message();
		//		message.setApp(this);
		//		message.setTitle(notification.getTitle());
		//		message.setAlert(notification.getAlert());
		//		message.setChannelId(findChannelId(apiKey));
		//		message.setCreationTime(new Datetime());
		//		message.setLastModified(message.getCreationTime());
		//		message.setWorkflow(workflow);
		//		message.create();
		//		notification.setChannelId(message.getChannelId().longValue());
		//		notification.setId(message.getId().longValue());
		//	}
	*/
}
