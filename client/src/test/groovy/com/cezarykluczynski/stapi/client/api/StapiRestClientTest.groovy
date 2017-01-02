package com.cezarykluczynski.stapi.client.api

class StapiRestClientTest extends AbstractStapiClientTest {

	private StapiRestClient stapiRestClient

	def "rest client can be instantiated with canonical URL"() {
		when:
		stapiRestClient = new StapiRestClient()

		then:
		stapiRestClient.seriesApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.performerApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.staffApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.characterApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.episodeApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.movieApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
	}

	def "rest client can be instantiated with custom URL"() {
		when:
		stapiRestClient = new StapiRestClient(CUSTOM_URL)

		then:
		stapiRestClient.seriesApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.performerApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.staffApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.characterApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.episodeApi.apiClient.basePath.contains(CUSTOM_URL)
		stapiRestClient.movieApi.apiClient.basePath.contains(CUSTOM_URL)
	}

}
