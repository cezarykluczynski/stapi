package com.cezarykluczynski.stapi.client.rest

import spock.lang.Specification

class StapiRestClientTest extends Specification {

	static final String CUSTOM_URL = 'http://localhost/stapi/'

	private StapiRestClient stapiRestClient

	void "rest client can be instantiated with canonical URL"() {
		when:
		stapiRestClient = new StapiRestClient()

		then:
		stapiRestClient.animalApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.astronomicalObjectApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.bookApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.bookCollectionApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.bookSeriesApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.characterApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.comicCollectionApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.comicsApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.comicSeriesApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.comicStripApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.companyApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.conflictApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.dataVersionApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.elementApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.episodeApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.foodApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.literatureApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.locationApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.magazineApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.magazineSeriesApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.materialApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.medicalConditionApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.movieApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.occupationApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.organizationApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.performerApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.seasonApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.seriesApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.soundtrackApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.spacecraftApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.spacecraftClassApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.speciesApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.staffApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.technologyApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.titleApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.tradingCardApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.tradingCardDeckApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.tradingCardSetApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.videoGameApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.videoReleaseApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
		stapiRestClient.weaponApi.apiClient.basePath.contains(StapiRestClient.CANONICAL_API_HTTPS_URL)
	}

	void "rest client can be instantiated with custom URL"() {
		when:
		stapiRestClient = new StapiRestClient(CUSTOM_URL)

		then:
		stapiRestClient.animalApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.astronomicalObjectApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.bookApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.bookCollectionApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.bookSeriesApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.characterApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.comicCollectionApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.comicsApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.comicSeriesApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.comicStripApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.companyApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.conflictApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.dataVersionApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.elementApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.episodeApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.foodApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.literatureApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.locationApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.magazineApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.magazineSeriesApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.materialApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.medicalConditionApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.movieApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.occupationApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.organizationApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.performerApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.seasonApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.seriesApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.soundtrackApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.spacecraftApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.spacecraftClassApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.speciesApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.staffApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.technologyApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.titleApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.tradingCardApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.tradingCardDeckApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.tradingCardSetApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.videoGameApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.videoReleaseApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.weaponApi.apiClient.basePath.contains(CUSTOM_URL)
	}

	void "REST client cannot be instantiated with URL that does not start with 'http'"() {
		when:
		stapiRestClient = new StapiRestClient('url/')

		then:
		thrown(IllegalArgumentException)
	}

	void "REST client cannot be instantiated with URL that does not end with slash"() {
		when:
		stapiRestClient = new StapiRestClient('http://url')

		then:
		thrown(IllegalArgumentException)
	}

	void "when null URL is passed, canonical URL is used"() {
		when:
		stapiRestClient = new StapiRestClient(null)

		then:
		stapiRestClient.apiUrl == StapiRestClient.CANONICAL_API_HTTPS_URL
	}

}
