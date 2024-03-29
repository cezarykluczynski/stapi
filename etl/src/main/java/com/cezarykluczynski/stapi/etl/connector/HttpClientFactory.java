package com.cezarykluczynski.stapi.etl.connector;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

@Service
public class HttpClientFactory {

	public HttpClient create() {
		return HttpClients.createDefault();
	}

}
