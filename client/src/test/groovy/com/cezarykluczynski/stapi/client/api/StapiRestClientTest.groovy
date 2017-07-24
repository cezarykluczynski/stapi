package com.cezarykluczynski.stapi.client.api

class StapiRestClientTest extends AbstractStapiClientTest {

	private StapiRestClient stapiRestClient

	void "rest client can be instantiated with canonical URL"() {
		when:
		stapiRestClient = new StapiRestClient()

		then:
		stapiRestClient.seriesApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.performerApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.staffApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.characterApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.episodeApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.movieApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.astronomicalObjectApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.companyApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.comicSeriesApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.comicsApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.comicStripApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.comicCollectionApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.speciesApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.organizationApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.foodApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.locationApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.bookSeriesApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.bookApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.magazineApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.magazineSeriesApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.literatureApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.seasonApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.videoReleaseApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.tradingCardSetApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
	}

	void "rest client can be instantiated with custom URL"() {
		when:
		stapiRestClient = new StapiRestClient(CUSTOM_URL)

		then:
		stapiRestClient.seriesApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.performerApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.staffApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.characterApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.episodeApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.movieApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.astronomicalObjectApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.companyApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.comicSeriesApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.comicsApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.comicStripApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.comicCollectionApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.speciesApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.organizationApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.foodApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.locationApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.bookSeriesApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.bookApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.magazineApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.magazineSeriesApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.literatureApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.seasonApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.videoReleaseApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.tradingCardSetApi.apiClient.basePath.contains(CUSTOM_URL)
	}

}
