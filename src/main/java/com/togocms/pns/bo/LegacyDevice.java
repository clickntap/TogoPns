package com.togocms.pns.bo;

public class LegacyDevice extends Device {

	public String getDomainName() {
		return getChannel();
	}

	public void setDomainName(String domainName) {
		setChannel(domainName);
	}

	public String getApnsToken() {
		return getToken();
	}

	public void setApnsToken(String apnsToken) {
		setToken(apnsToken);
	}

	public String getPlatformId() {
		try {
			return getPlatform().toString();
		} catch (Exception e) {
			return null;
		}
	}

	public void setPlatformId(String platformId) {
		try {
			setPlatform(Integer.parseInt(platformId));
		} catch (Exception e) {
			setPlatform(null);
		}
	}

}
