package com.togocms.pns.firebase;

import org.json.JSONObject;

public class FirebaseResponse {
	private JSONObject json;
	private int statusCode;

	public FirebaseResponse(JSONObject json, int statusCode) {
		this.json = json;
		this.statusCode = statusCode;
	}

	public JSONObject getJson() {
		return json;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String toString() {
		return new StringBuffer("HTTP Status Code: \n").append(getStatusCode()).append("\nJson: \n").append(getJson().toString(2)).toString();
	}
}
