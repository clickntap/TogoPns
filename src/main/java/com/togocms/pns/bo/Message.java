package com.togocms.pns.bo;

import com.clickntap.hub.BO;
import com.clickntap.tool.types.Datetime;

public class Message extends BO {
	private Number channelId;
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

}
