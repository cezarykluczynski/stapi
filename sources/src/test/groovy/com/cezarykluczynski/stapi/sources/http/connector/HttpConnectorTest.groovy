package com.cezarykluczynski.stapi.sources.http.connector

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.message.BasicNameValuePair
import spock.lang.Specification

class HttpConnectorTest extends Specification {

	private static final String URL = 'URL'
	private static final String NAME_1 = 'NAME_1'
	private static final String VALUE_1 = 'VALUE_1'
	private static final String NAME_2 = 'NAME_2'
	private static final String VALUE_2 = 'VALUE_2'

	private HttpClientFactory httpClientFactory

	private HttpClient httpClient

	private HttpConnector httpConnector

	void setup() {
		httpClientFactory = Mock()
		httpClient = Mock()
		httpConnector = new HttpConnector(httpClientFactory)
	}

	void "executes request without errors"() {
		given:
		NameValuePair nameValuePair1 = new BasicNameValuePair(NAME_1, VALUE_1)
		NameValuePair nameValuePair2 = new BasicNameValuePair(NAME_2, VALUE_2)
		List<NameValuePair> params = [nameValuePair1, nameValuePair2]
		HttpResponse httpResponse = Mock()
		HttpEntity httpEntity = Mock()

		when:
		HttpEntity response = httpConnector.post(URL, params)

		then:
		1 * httpClientFactory.create() >> httpClient
		1 * httpClient.execute(_ as HttpUriRequest) >> httpResponse
		1 * httpResponse.entity >> httpEntity
		0 * _
		response == httpEntity
	}

	void "executes request while there were network troubles"() {
		given:
		Long previousFirstPostpone = HttpConnector.NETWORK_TROUBLE_POSTPONES_TIMES[0]
		HttpConnector.NETWORK_TROUBLE_POSTPONES_TIMES[0] = 5L
		NameValuePair nameValuePair1 = new BasicNameValuePair(NAME_1, VALUE_1)
		NameValuePair nameValuePair2 = new BasicNameValuePair(NAME_2, VALUE_2)
		List<NameValuePair> params = [nameValuePair1, nameValuePair2]
		HttpResponse httpResponse = Mock()
		HttpEntity httpEntity = Mock()

		when:
		HttpEntity response = httpConnector.post(URL, params)

		then:
		1 * httpClientFactory.create() >> httpClient
		1 * httpClient.execute(_ as HttpUriRequest) >> null

		then:
		1 * httpClientFactory.create() >> httpClient
		1 * httpClient.execute(_ as HttpUriRequest) >> httpResponse
		1 * httpResponse.entity >> httpEntity
		0 * _
		response == httpEntity

		cleanup:
		HttpConnector.NETWORK_TROUBLE_POSTPONES_TIMES[0] = previousFirstPostpone
	}

}
