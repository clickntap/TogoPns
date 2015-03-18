package com.togocms.pns.bo;

import com.clickntap.hub.BO;

public class Channel extends BO {

	private String name;
	private String secretKey;
	private String keyStorePath;
	private String keyStorePassword;
	private Number production;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
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

	public Number getProduction() {
		return production;
	}

	public void setProduction(Number production) {
		this.production = production;
	}

}
