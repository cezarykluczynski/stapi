package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.ApiRequest;

public class ApiKeySupplier {

	private final String apiKey;

	public ApiKeySupplier(String apiKey) {
		this.apiKey = apiKey;
	}

	void supply(ApiRequest apiRequest) {
		if (apiRequest.getApiKey() == null) {
			apiRequest.setApiKey(apiKey);
		}
	}

}
