package com.cezarykluczynski.stapi.client.api

class StapiSoapClientTest extends AbstractStapiClientTest {

	private StapiSoapClient stapiSoapClient

	void "soap client can be instantiated with canonical URL"() {
		when:
		stapiSoapClient = new StapiSoapClient()

		then:
		((String) toBindingProvider(stapiSoapClient.seriesPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.performerPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.staffPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.characterPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.episodePortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.moviePortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.astronomicalObjectPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.companyPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.comicSeriesPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.comicsPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.comicStripPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.comicCollectionPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.speciesPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.organizationPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.foodPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.locationPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.bookSeriesPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.bookPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.magazinePortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.magazineSeriesPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.literaturePortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.seasonPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.videoReleasePortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.tradingCardSetPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.tradingCardDeckPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.tradingCardPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
	}

	void "soap client can be instantiated with custom URL"() {
		when:
		stapiSoapClient = new StapiSoapClient(CUSTOM_URL)

		then:
		((String) toBindingProvider(stapiSoapClient.seriesPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.performerPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.staffPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.characterPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.episodePortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.moviePortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.astronomicalObjectPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.companyPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.comicSeriesPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.comicsPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.comicStripPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.comicCollectionPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.speciesPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.organizationPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.foodPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.locationPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.bookSeriesPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.bookPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.magazinePortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.magazineSeriesPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.literaturePortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.seasonPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.videoReleasePortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.tradingCardSetPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.tradingCardDeckPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.tradingCardPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
	}

}
