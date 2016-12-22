package com.togocms.pns.bo;

import java.util.List;

import com.clickntap.hub.BO;

public class PnsChannel extends BO {
	private String androidType;
	private String apiKey;

	private String androidSecretKey;
	private String appleKeyStore;
	private String appleKeyStorePassword;

	private Number production;

	public String getAndroidType() {
		return androidType;
	}

	public void setAndroidType(String androidType) {
		this.androidType = androidType;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getAndroidSecretKey() {
		return androidSecretKey;
	}

	public void setAndroidSecretKey(String androidSecretKey) {
		this.androidSecretKey = androidSecretKey;
	}

	public String getAppleKeyStore() {
		return appleKeyStore;
	}

	public void setAppleKeyStore(String appleKeyStore) {
		this.appleKeyStore = appleKeyStore;
	}

	public String getAppleKeyStorePassword() {
		return appleKeyStorePassword;
	}

	public void setAppleKeyStorePassword(String appleKeyStorePassword) {
		this.appleKeyStorePassword = appleKeyStorePassword;
	}

	public Number getProduction() {
		return production;
	}

	public void setProduction(Number production) {
		this.production = production;
	}

	public List<PnsDevice> getAndroidDevices() throws Exception {
		return getApp().getBOListByFilter(PnsDevice.class, this, "android-devices");
	}

}
