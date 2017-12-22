package com.cezarykluczynski.stapi.client.api

class StapiRestClientTest extends AbstractStapiClientTest {

	private static final String API_KEY = 'API_KEY'

	private StapiRestClient stapiRestClient

	void "rest client can be instantiated with canonical URL"() {
		when:
		stapiRestClient = new StapiRestClient(null, null)

		then:
		stapiRestClient.animalApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.astronomicalObjectApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.bookApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.bookCollectionApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.bookSeriesApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.characterApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.comicCollectionApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.comicsApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.comicSeriesApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.comicStripApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.companyApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.conflictApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.elementApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.episodeApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.foodApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.literatureApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.locationApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.magazineApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.magazineSeriesApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.materialApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.medicalConditionApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.movieApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.occupationApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.organizationApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.performerApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.seasonApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.seriesApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.soundtrackApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.spacecraftApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.spacecraftClassApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.speciesApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.staffApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.technologyApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.titleApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.tradingCardApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.tradingCardDeckApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.tradingCardSetApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.videoGameApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.videoReleaseApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
		stapiRestClient.weaponApi.apiClient.basePath.contains(StapiClient.CANONICAL_API_URL)
	}

	void "rest client can be instantiated with custom URL"() {
		when:
		stapiRestClient = new StapiRestClient(CUSTOM_URL, null)

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

	void "rest client has API key aware proxies"() {
		when:
		stapiRestClient = new StapiRestClient(null, API_KEY)

		then:
		stapiRestClient.animal.apiKey == API_KEY
		stapiRestClient.astronomicalObject.apiKey == API_KEY
		stapiRestClient.book.apiKey == API_KEY
		stapiRestClient.bookCollection.apiKey == API_KEY
		stapiRestClient.bookSeries.apiKey == API_KEY
		stapiRestClient.character.apiKey == API_KEY
		stapiRestClient.comicCollection.apiKey == API_KEY
		stapiRestClient.comics.apiKey == API_KEY
		stapiRestClient.comicSeries.apiKey == API_KEY
		stapiRestClient.comicStrip.apiKey == API_KEY
		stapiRestClient.company.apiKey == API_KEY
		stapiRestClient.conflict.apiKey == API_KEY
		stapiRestClient.element.apiKey == API_KEY
		stapiRestClient.episode.apiKey == API_KEY
		stapiRestClient.food.apiKey == API_KEY
		stapiRestClient.literature.apiKey == API_KEY
		stapiRestClient.location.apiKey == API_KEY
		stapiRestClient.magazine.apiKey == API_KEY
		stapiRestClient.magazineSeries.apiKey == API_KEY
		stapiRestClient.material.apiKey == API_KEY
		stapiRestClient.medicalCondition.apiKey == API_KEY
		stapiRestClient.movie.apiKey == API_KEY
		stapiRestClient.occupation.apiKey == API_KEY
		stapiRestClient.organization.apiKey == API_KEY
		stapiRestClient.performer.apiKey == API_KEY
		stapiRestClient.season.apiKey == API_KEY
		stapiRestClient.series.apiKey == API_KEY
		stapiRestClient.soundtrack.apiKey == API_KEY
		stapiRestClient.spacecraft.apiKey == API_KEY
		stapiRestClient.spacecraftClass.apiKey == API_KEY
		stapiRestClient.species.apiKey == API_KEY
		stapiRestClient.staff.apiKey == API_KEY
		stapiRestClient.technology.apiKey == API_KEY
		stapiRestClient.title.apiKey == API_KEY
		stapiRestClient.tradingCard.apiKey == API_KEY
		stapiRestClient.tradingCardDeck.apiKey == API_KEY
		stapiRestClient.tradingCardSet.apiKey == API_KEY
		stapiRestClient.videoGame.apiKey == API_KEY
		stapiRestClient.videoRelease.apiKey == API_KEY
		stapiRestClient.weapon.apiKey == API_KEY
	}

	void "REST client cannot be instantiated with URL that does not start with 'http'"() {
		when:
		stapiRestClient = new StapiRestClient('url/', API_KEY)

		then:
		thrown(IllegalArgumentException)
	}

	void "REST client cannot be instantiated with URL that does not end with slash"() {
		when:
		stapiRestClient = new StapiRestClient('http://url', API_KEY)

		then:
		thrown(IllegalArgumentException)
	}

	void "when null URL is passed, canonical URL is used"() {
		when:
		stapiRestClient = new StapiRestClient(null, API_KEY)

		then:
		stapiRestClient.apiUrl == StapiClient.CANONICAL_API_URL
	}

}
