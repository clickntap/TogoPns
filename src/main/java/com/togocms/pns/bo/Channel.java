package com.togocms.pns.bo;

import java.util.List;

import com.clickntap.hub.BO;

public class Channel extends BO {

	private String name;
	private String apiKey;
	private String secretKey;
	private String keyStore;
	private String keyStorePassword;
	private Number production;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getKeyStore() {
		return keyStore;
	}

	public void setKeyStore(String keyStore) {
		this.keyStore = keyStore;
	}

	public String getKeyStorePassword() {
		return keyStorePassword;
	}

	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

	public Number getProduction() {
		return production;
	}

	public void setProduction(Number production) {
		this.production = production;
	}

	public List<Device> getDevices() throws Exception {
		return getApp().getBOListByFilter(Device.class, this, "devices");
	}

}
