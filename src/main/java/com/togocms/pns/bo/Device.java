package com.togocms.pns.bo;

import com.clickntap.hub.BO;
import com.clickntap.tool.types.Datetime;

public class Device extends BO {

	private String token;
	private String uid;
	private Number platform;
	private Number userId;
	private Number channelId;
	private String channel;
	private Datetime creationTime;
	private Datetime lastModified;
	private Number disabled;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Number getPlatform() {
		return platform;
	}

	public void setPlatform(Number platform) {
		this.platform = platform;
	}

	public Number getUserId() {
		return userId;
	}

	public void setUserId(Number userId) {
		this.userId = userId;
	}

	public Number getChannelId() {
		return channelId;
	}

	public void setChannelId(Number channelId) {
		this.channelId = channelId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Datetime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Datetime creationTime) {
		this.creationTime = creationTime;
	}

	public Datetime getLastModified() {
		return lastModified;
	}

	public void setLastModified(Datetime lastModified) {
		this.lastModified = lastModified;
	}

	public Number getDisabled() {
		return disabled;
	}

	public void setDisabled(Number disabled) {
		this.disabled = disabled;
	}

	public Number update() throws Exception {
		if (getChannel() != null) {
			Channel channel = new Channel();
			channel.setApp(getApp());
			channel.setName(getChannel());
			channel.read("name");
			setChannelId(channel.getId());
		}
		if (getId() == null) {
			Device device = new Device();
			device.setApp(getApp());
			device.setToken(getToken());
			device.setChannelId(getChannelId());
			device.read("token+channel");
			setId(device.getId());
			if (getId() == null) {
				device.setCreationTime(new Datetime());
				device.setChannelId(getChannelId());
				device.setPlatform(getPlatform());
				setId(device.create());
			}
		}
		setLastModified(new Datetime());
		setDisabled(0);
		return super.update();
	}

}
