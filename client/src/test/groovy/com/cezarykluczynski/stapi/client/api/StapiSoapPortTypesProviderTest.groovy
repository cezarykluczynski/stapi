package com.cezarykluczynski.stapi.client.api

class StapiSoapPortTypesProviderTest extends AbstractStapiClientTest {

	void "provider can be instantiated with canonical URL"() {
		when:
		StapiSoapPortTypesProvider stapiSoapPortTypesProvider = new StapiSoapPortTypesProvider()

		then:
		((String) toBindingProvider(stapiSoapPortTypesProvider.seriesPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapPortTypesProvider.performerPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapPortTypesProvider.staffPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapPortTypesProvider.characterPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapPortTypesProvider.episodePortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapPortTypesProvider.moviePortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
	}

	void "provider can be instantiated with custom url"() {
		when:
		StapiSoapPortTypesProvider stapiSoapPortTypesProvider = new StapiSoapPortTypesProvider(CUSTOM_URL)

		then:
		((String) toBindingProvider(stapiSoapPortTypesProvider.seriesPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapPortTypesProvider.performerPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapPortTypesProvider.staffPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapPortTypesProvider.characterPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapPortTypesProvider.episodePortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapPortTypesProvider.moviePortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
	}

}
