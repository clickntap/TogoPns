package com.togocms.pns.bo;

import com.clickntap.hub.BO;
import com.clickntap.utils.ConstUtils;

public class PnsMessage extends BO {
	private Number channelId;
	private String title;
	private String message;
	private String targetIds;
	private Number androidSent;
	private Number androidFails;

	public Number getChannelId() {
		return channelId;
	}

	public void setChannelId(Number channelId) {
		this.channelId = channelId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTargetIds() {
		return targetIds;
	}

	public void setTargetIds(String targetIds) {
		this.targetIds = targetIds;
	}

	public PnsChannel getChannel() throws Exception {
		return getApp().getBO(PnsChannel.class, getChannelId());
	}

	public Number getAndroidSent() {
		return androidSent;
	}

	public void setAndroidSent(Number androidSent) {
		this.androidSent = androidSent;
	}

	public Number getAndroidFails() {
		return androidFails;
	}

	public void setAndroidFails(Number androidFails) {
		this.androidFails = androidFails;
	}

	public String getSound() {
		return ConstUtils.EMPTY;
	}

}
