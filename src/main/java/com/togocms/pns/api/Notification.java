package com.togocms.pns.api;

import java.util.List;

public interface Notification {

	public String getKeyStorePath() throws Exception;

	public String getKeyStorePassword() throws Exception;

	public String getSecretKey() throws Exception;

	public List<String> getTokens() throws Exception;

	public String getTitle() throws Exception;

	public String getAlert() throws Exception;

	public Boolean isProduction();

	public List<String> send() throws Exception;

	public void addToken(String token) throws Exception;
}
