package com.togocms.pns.bo;

import java.util.List;

import com.clickntap.hub.BO;
import com.clickntap.tool.types.Datetime;

public class Message extends BO {
	private Number channelId;
	private String title;
	private String alert;
	private Number workflow;
	private Datetime creationTime;
	private Datetime lastModified;

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

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public Number getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Number workflow) {
		this.workflow = workflow;
	}

	public Datetime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Datetime creationDate) {
		this.creationTime = creationDate;
	}

	public Datetime getLastModified() {
		return lastModified;
	}

	public void setLastModified(Datetime lastModified) {
		this.lastModified = lastModified;
	}

	public Channel getChannel() throws Exception {
		return getApp().getBO(Channel.class, getChannelId());
	}

	public List<Push> getNextAndroidPushes() throws Exception {
		return getApp().getBOListByFilter(Push.class, this, "android-pushes");
	}

}
