package com.cezarykluczynski.stapi.client.api

class StapiSoapClientTest extends AbstractStapiClientTest {

	private StapiSoapClient stapiSoapClient

	void "soap client can be instantiated"() {
		when: 'it is instantiated with canonical URL'
		stapiSoapClient = new StapiSoapClient(null)

		then: 'canonical url is set'
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
		((String) toBindingProvider(stapiSoapClient.bookCollectionPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.magazinePortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.magazineSeriesPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.literaturePortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.seasonPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.videoReleasePortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.tradingCardSetPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.tradingCardDeckPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.tradingCardPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.videoGamePortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.soundtrackPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.weaponPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.spacecraftClassPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.spacecraftPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.titlePortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.materialPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.conflictPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.animalPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.elementPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.medicalConditionPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.technologyPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)
		((String) toBindingProvider(stapiSoapClient.occupationPortType).requestContext.get(URL_KEY)).contains(StapiClient.CANONICAL_API_URL)

		then: 'API aware proxies are set'
		stapiSoapClient.series != null
		stapiSoapClient.performer != null
		stapiSoapClient.staff != null
		stapiSoapClient.character != null
		stapiSoapClient.episode != null
		stapiSoapClient.movie != null
		stapiSoapClient.astronomicalObject != null
		stapiSoapClient.company != null
		stapiSoapClient.comicSeries != null
		stapiSoapClient.comics != null
		stapiSoapClient.comicStrip != null
		stapiSoapClient.comicCollection != null
		stapiSoapClient.species != null
		stapiSoapClient.organization != null
		stapiSoapClient.food != null
		stapiSoapClient.location != null
		stapiSoapClient.bookSeries != null
		stapiSoapClient.book != null
		stapiSoapClient.bookCollection != null
		stapiSoapClient.magazine != null
		stapiSoapClient.magazineSeries != null
		stapiSoapClient.literature != null
		stapiSoapClient.season != null
		stapiSoapClient.videoRelease != null
		stapiSoapClient.tradingCardSet != null
		stapiSoapClient.tradingCardDeck != null
		stapiSoapClient.tradingCard != null
		stapiSoapClient.videoGame != null
		stapiSoapClient.soundtrack != null
		stapiSoapClient.weapon != null
		stapiSoapClient.spacecraftClass != null
		stapiSoapClient.spacecraft != null
		stapiSoapClient.title != null
		stapiSoapClient.material != null
		stapiSoapClient.conflict != null
		stapiSoapClient.animal != null
		stapiSoapClient.element != null
		stapiSoapClient.medicalCondition != null
		stapiSoapClient.technology != null
		stapiSoapClient.occupation != null
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
		((String) toBindingProvider(stapiSoapClient.bookCollectionPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.magazinePortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.magazineSeriesPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.literaturePortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.seasonPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.videoReleasePortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.tradingCardSetPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.tradingCardDeckPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.tradingCardPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.videoGamePortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.soundtrackPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.weaponPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.spacecraftClassPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.spacecraftPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.titlePortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.materialPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.conflictPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.animalPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.elementPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.medicalConditionPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.technologyPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
		((String) toBindingProvider(stapiSoapClient.occupationPortType).requestContext.get(URL_KEY)).contains(CUSTOM_URL)
	}

	void "SOAP client cannot be instantiated with URL that does not start with 'http'"() {
		when:
		stapiSoapClient = new StapiSoapClient('url/')

		then:
		thrown(IllegalArgumentException)
	}

	void "SOAP client cannot be instantiated with URL that does not end with slash"() {
		when:
		stapiSoapClient = new StapiSoapClient('http://url')

		then:
		thrown(IllegalArgumentException)
	}

	void "when null URL is passed, canonical URL is used"() {
		when:
		stapiSoapClient = new StapiSoapClient(null)

		then:
		stapiSoapClient.apiUrl == StapiClient.CANONICAL_API_URL
	}

}
