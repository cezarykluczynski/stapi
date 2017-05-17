package com.cezarykluczynski.stapi.server.common.throttle.credential;

public class RequestCredential {

	static final String API_KEY = "apiKey";

	private RequestCredentialType requestCredentialType;

	private String ipAddress;

	private String apiKey;

	public RequestCredentialType getRequestCredentialType() {
		return requestCredentialType;
	}

	public void setRequestCredentialType(RequestCredentialType requestCredentialType) {
		this.requestCredentialType = requestCredentialType;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

}
