package com.cezarykluczynski.stapi.client.api;

abstract class AbstractStapiClient {

	String changeBaseUrl(String baseUrl, String serviceUrl) {
		return serviceUrl.replace(StapiClient.CANONICAL_API_URL, baseUrl);
	}

	String defaultIfBlank(String string, String defaultString) {
		return string != null && !string.isEmpty() ? string : defaultString;
	}

	String validateUrl(String url) {
		if (!url.startsWith("http")) {
			throw new IllegalArgumentException(String.format("URL %s must start with \"http\" or \"https\"", url));
		}

		if (!url.endsWith("/")) {
			throw new IllegalArgumentException(String.format("URL %s must end with slash", url));
		}

		return url;
	}

}
