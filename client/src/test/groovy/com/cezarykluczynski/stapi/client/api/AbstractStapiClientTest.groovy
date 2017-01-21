package com.cezarykluczynski.stapi.client.api

import spock.lang.Specification

import javax.xml.ws.BindingProvider

abstract class AbstractStapiClientTest extends Specification {

	protected static final String CUSTOM_URL = 'http://localhost/stapi/'
	protected static final String URL_KEY = BindingProvider.ENDPOINT_ADDRESS_PROPERTY

	protected static BindingProvider toBindingProvider(Object object) {
		(BindingProvider) object
	}

}
