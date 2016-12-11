package com.cezarykluczynski.stapi.client.api

class StapiSoapClientTest extends AbstractStapiClientTest {

	private StapiSoapClient stapiSoapClient

	def "soap client can be instantiated with canonical URL"() {
		when:
		stapiSoapClient = new StapiSoapClient()

		then:
		((String) toBindingProvider(stapiSoapClient.seriesPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.performerPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.staffPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.characterPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.episodePortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
	}

	def "soap client can be instantiated with custom URL"() {
		when:
		stapiSoapClient = new StapiSoapClient(CUSTOM_URL)

		then:
		((String) toBindingProvider(stapiSoapClient.seriesPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.performerPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.staffPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.characterPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.episodePortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
	}

}
