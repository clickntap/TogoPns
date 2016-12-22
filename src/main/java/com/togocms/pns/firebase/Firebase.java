package com.togocms.pns.firebase;

import java.io.ByteArrayOutputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import com.clickntap.utils.ConstUtils;

public class Firebase {
	private static final String FIREBASE_SERVER = "https://fcm.googleapis.com/fcm/send";
	private String clientId;

	public Firebase(String clientId) {
		this.clientId = clientId;
	}

	public FirebaseResponse sendMessage(String to, String title, String message) throws Exception {
		return sendMessage(to, title, message, null);
	}

	public FirebaseResponse sendMessage(String to, String title, String message, String sound) throws Exception {
		JSONObject json = new JSONObject();
		JSONObject notification = new JSONObject();
		notification.put("title", title);
		notification.put("body", message);
		if (sound != null) {
			notification.put("sound", sound);
		}
		json.put("notification", notification);
		json.put("to", to);
		//System.out.println(json.toString(2));
		return apiRequest(FIREBASE_SERVER, HttpPost.METHOD_NAME, json);
	}

	private FirebaseResponse apiRequest(String endpoint, String methodName, JSONObject message) throws Exception {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpRequestBase request = null;
		String url = null;
		if (endpoint.startsWith("http")) {
			url = endpoint;
		} else {
			url = new StringBuffer(FIREBASE_SERVER).append(endpoint).toString();
		}
		//System.out.println(url);
		if (methodName.equals(HttpGet.METHOD_NAME)) {
			request = new HttpGet(url);
		} else if (methodName.equals(HttpPost.METHOD_NAME)) {
			request = new HttpPost(url);
		} else if (methodName.equals(HttpPut.METHOD_NAME)) {
			request = new HttpPut(url);
		} else if (methodName.equals(HttpDelete.METHOD_NAME)) {
			request = new HttpDelete(url);
		} else if (methodName.equals(HttpPatch.METHOD_NAME)) {
			request = new HttpPatch(url);
		}
		request.addHeader("Authorization", new StringBuffer("key=").append(clientId).toString());
		request.addHeader("Content-Type", "application/json");
		HttpEntity entity = new StringEntity(message.toString(), ConstUtils.UTF_8);
		if (entity != null) {
			if (request instanceof HttpPost) {
				((HttpPost) request).setEntity(entity);
			} else if (request instanceof HttpPatch) {
				((HttpPatch) request).setEntity(entity);
			} else if (request instanceof HttpPut) {
				((HttpPut) request).setEntity(entity);
			}
		}
		CloseableHttpResponse response = client.execute(request);
		String responseAsString = null;
		int statusCode = response.getStatusLine().getStatusCode();
		if (methodName.equals(HttpPut.METHOD_NAME) || methodName.equals(HttpDelete.METHOD_NAME)) {
			JSONObject out = new JSONObject();
			for (Header header : response.getAllHeaders()) {
				out.put(header.getName(), header.getValue());
			}
			responseAsString = out.toString();
		} else if (statusCode != 204) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			response.getEntity().writeTo(out);
			responseAsString = out.toString("UTF-8");
			out.close();
		}
		JSONObject json = null;
		try {
			json = new JSONObject(responseAsString);
		} catch (Exception e) {
			json = new JSONObject();
		}
		FirebaseResponse instagramResponse = new FirebaseResponse(json, statusCode);
		response.close();
		client.close();
		return instagramResponse;
	}
}
