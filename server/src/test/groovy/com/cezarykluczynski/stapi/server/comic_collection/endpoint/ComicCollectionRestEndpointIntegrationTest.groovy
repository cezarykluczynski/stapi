package com.cezarykluczynski.stapi.server.comic_collection.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_COMIC_COLLECTIONS)
})
class ComicCollectionRestEndpointIntegrationTest extends AbstractComicCollectionEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	@Requires({
		StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_COMICS)
	})
	void "gets comic collection by UID"() {
		when:
		ComicCollectionFullResponse comicCollectionFullResponse = stapiRestClient.comicCollectionApi.comicCollectionGet('CLMA0000108975', null)

		then:
		comicCollectionFullResponse.comicCollection.title == 'Convergence'
		comicCollectionFullResponse.comicCollection.comics.size() >= 3
	}

	void "'Star Trek Ultimate Edition' is among collections with more than 500 pages"() {
		when:
		ComicCollectionBaseResponse comicCollectionBaseResponse = stapiRestClient.comicCollectionApi.comicCollectionSearchPost(null, null, null, null,
				null, null, null, 500, null, null, null, null, null, null)

		then:
		comicCollectionBaseResponse.comicCollections.stream()
				.anyMatch { it -> it.title == 'Star Trek Ultimate Edition' }
	}

}
