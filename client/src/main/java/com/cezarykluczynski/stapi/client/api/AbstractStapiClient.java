package com.cezarykluczynski.stapi.client.api;

abstract class AbstractStapiClient {

	String changeBaseUrl(String baseUrl, String serviceUrl) {
		return serviceUrl.replace(StapiClient.CANONICAL_API_URL, baseUrl);
	}

}
