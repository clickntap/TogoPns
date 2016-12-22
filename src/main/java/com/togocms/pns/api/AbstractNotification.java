package com.togocms.pns.api;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNotification implements Notification {

	private String secretKey;
	private String keyStorePath;
	private String keyStorePassword;
	private String title;
	private String alert;
	private List<String> tokens;
	private Boolean production;

	public AbstractNotification() {
		tokens = new ArrayList<String>();
		title = "";
		alert = "";
		production = true;
	}

	public void addToken(String token) throws Exception {
		tokens.add(token);
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
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

	public List<String> getTokens() {
		return tokens;
	}

	public void setTokens(List<String> tokens) {
		this.tokens = tokens;
	}

	public Boolean isProduction() {
		return production;
	}

	public void setProduction(Boolean production) {
		this.production = production;
	}

	public String getKeyStorePath() {
		return keyStorePath;
	}

	public void setKeyStorePath(String keyStorePath) {
		this.keyStorePath = keyStorePath;
	}

	public String getKeyStorePassword() {
		return keyStorePassword;
	}

	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

}
