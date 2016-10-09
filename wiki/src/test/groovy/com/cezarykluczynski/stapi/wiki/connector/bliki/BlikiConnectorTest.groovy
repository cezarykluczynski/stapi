package com.cezarykluczynski.stapi.wiki.connector.bliki

import com.google.common.collect.Maps
import info.bliki.api.Connector
import info.bliki.api.User
import org.apache.commons.io.IOUtils
import org.apache.http.Header
import org.apache.http.HeaderElement
import org.apache.http.HttpEntity
import org.apache.http.HttpStatus
import org.apache.http.StatusLine
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClientBuilder
import spock.lang.Specification

class BlikiConnectorTest extends Specification {

	private static final String XML = '<?xml version="1.0"?><root></root>'
	private static final String TITLE = 'TITLE'

	private User userMock

	private Connector connector

	private HttpClientBuilder httpClientBuilderMock

	private BlikiConnector blikiConnector

	def setup() {
		userMock = Mock(User) {
			getActionUrl() >> 'ACTION_URL'
		}

		httpClientBuilderMock = Mock(HttpClientBuilder) {
			build() >> Mock(CloseableHttpClient) {
				execute(*_) >> Mock(CloseableHttpResponse) {
					getStatusLine() >> Mock(StatusLine) {
						getStatusCode() >> HttpStatus.SC_OK
					}
					getEntity() >> Mock(HttpEntity) {
						getContentType() >> Mock(Header) {
							getElements() >> [
									Mock(HeaderElement) {
										getName() >> 'text/xml'
										getParameters() >> []
									}
							]
						}
						getContent() >> IOUtils.toInputStream(XML)
						getContentLength() >> XML.length()
					}

				}
			}
		}

		connector = new Connector(httpClientBuilderMock)
	}

	def "reads XML"() {
		given:
		blikiConnector = new BlikiConnector(userMock, connector)

		when:
		String xml = blikiConnector.readXML(Maps.newHashMap())

		then:
		xml == XML
	}

	def "throws RuntimeError on any error"() {
		given: 'connector without sendXML method is injected'
		blikiConnector = new BlikiConnector(userMock, Mock(Connector))

		when:
		blikiConnector.readXML(Maps.newHashMap())

		then:
		thrown(RuntimeException)
	}

	def "gets page as string"() {
		given:
		blikiConnector = new BlikiConnector(userMock, connector)

		when:
		String xml = blikiConnector.getPage(TITLE)

		then:
		xml == XML
	}

}
