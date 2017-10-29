package com.cezarykluczynski.stapi.sources.common.service

import com.squareup.okhttp.Call
import com.squareup.okhttp.Headers
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Protocol
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import com.squareup.okhttp.ResponseBody
import com.squareup.okhttp.internal.http.RealResponseBody
import okio.BufferedSource
import spock.lang.Specification

class UrlContentRetrieverTest extends Specification {

	private static final String URL = 'http://stapi.co/'
	private static final String BODY = 'BODY'

	private OkHttpClient okHttpClientMock

	private UrlContentRetriever urlContentRetriever

	void setup() {
		okHttpClientMock = Mock()
		urlContentRetriever = new UrlContentRetriever(okHttpClientMock)
	}

	void "retrieves URL body"() {
		given:
		Request request = new Request.Builder()
				.url(URL)
				.build()
		Call call = Mock()
		BufferedSource bufferedSource = Mock()
		Headers headers = new Headers.Builder().build()
		ResponseBody responseBody = new RealResponseBody(headers, bufferedSource)
		Response response = new Response.Builder()
				.request(request)
				.protocol(Protocol.HTTP_1_1)
				.code(200)
				.body(responseBody)
				.build()

		when:
		String body = urlContentRetriever.getBody(URL)

		then:
		1 * okHttpClientMock.newCall(_ as Request) >> call
		1 * call.execute() >> response
		1 * bufferedSource.readByteArray() >> BODY.bytes
		1 * bufferedSource.close()
		0 * _
		body == BODY
	}

	void "returns null when body cannot be retrieved"() {
		given:
		Request request = new Request.Builder()
				.url(URL)
				.build()
		Call call = Mock()
		BufferedSource bufferedSource = Mock()
		Headers headers = new Headers.Builder().build()
		ResponseBody responseBody = new RealResponseBody(headers, bufferedSource)
		Response response = new Response.Builder()
				.request(request)
				.protocol(Protocol.HTTP_1_1)
				.code(200)
				.body(responseBody)
				.build()

		when:
		String body = urlContentRetriever.getBody(URL)

		then:
		1 * okHttpClientMock.newCall(_ as Request) >> call
		1 * call.execute() >> response
		1 * bufferedSource.readByteArray() >> {
			throw new IOException()
		}
		1 * bufferedSource.close()
		0 * _
		body == null
	}

}
