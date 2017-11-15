package com.cezarykluczynski.stapi.server.common.throttle.credential;

import org.apache.cxf.message.Message;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class RequestCredentialProvider {

	private final HttpServletRequest httpServletRequest;

	private final MessageContentExtractor messageContentExtractor;

	private final SoapApiKeyExtractor soapApiKeyExtractor;

	private final RestApiKeyExtractor restApiKeyExtractor;

	public RequestCredentialProvider(HttpServletRequest httpServletRequest, MessageContentExtractor messageContentExtractor,
			SoapApiKeyExtractor soapApiKeyExtractor, RestApiKeyExtractor restApiKeyExtractor) {
		this.httpServletRequest = httpServletRequest;
		this.messageContentExtractor = messageContentExtractor;
		this.soapApiKeyExtractor = soapApiKeyExtractor;
		this.restApiKeyExtractor = restApiKeyExtractor;
	}

	public RequestCredential provideRequestCredential(Message message) {
		RequestCredential requestCredential = new RequestCredential();
		String apiKey = tryExtractApiKey(message);
		if (apiKey != null) {
			requestCredential.setRequestCredentialType(RequestCredentialType.API_KEY);
			requestCredential.setApiKey(apiKey);
		} else {
			requestCredential.setRequestCredentialType(RequestCredentialType.IP_ADDRESS);
		}

		requestCredential.setIpAddress(httpServletRequest.getRemoteAddr());
		return requestCredential;
	}

	private String tryExtractApiKey(Message message) {
		String requestUri = httpServletRequest.getRequestURI();
		if (requestUri.contains("/soap/")) {
			return tryExtractSoapApiKey(message);
		} else if (requestUri.contains("/rest/")) {
			return tryExtractRestApiKey();
		}
		return null;
	}

	private String tryExtractRestApiKey() {
		return restApiKeyExtractor.extract(httpServletRequest);
	}

	private String tryExtractSoapApiKey(Message message) {
		String requestContent = messageContentExtractor.extract(message);
		return soapApiKeyExtractor.extract(requestContent);
	}

}
