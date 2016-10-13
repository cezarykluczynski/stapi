package com.cezarykluczynski.stapi.client.api

import spock.lang.Specification

import javax.xml.ws.BindingProvider

class StapiSoapPortTypesProviderTest extends Specification {

	private static final String CUSTOM_URL = "http://localhost/stapi/"
	private static final String URL_KEY = BindingProvider.ENDPOINT_ADDRESS_PROPERTY

	def "provider can be instantiated with standard URL"() {
		when:
		StapiSoapPortTypesProvider stapiSoapClient = new StapiSoapPortTypesProvider()

		then:
		stapiSoapClient.seriesPortType != null
	}

	def "provider can be instantiated with custom url"() {
		when:
		StapiSoapPortTypesProvider stapiSoapClient = new StapiSoapPortTypesProvider(CUSTOM_URL)

		then:
		((String) toBindingProvider(stapiSoapClient.seriesPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
	}

	private static BindingProvider toBindingProvider(Object object) {
		return (BindingProvider) object
	}

}