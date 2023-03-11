package com.cezarykluczynski.stapi.server.comic_collection.endpoint

import com.cezarykluczynski.stapi.client.api.dto.ComicCollectionSearchCriteria
import com.cezarykluczynski.stapi.client.rest.model.ComicCollectionBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.ComicCollectionV2FullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_COMIC_COLLECTIONS)
})
class ComicCollectionRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	@Requires({
		StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_COMICS)
	})
	void "gets comic collection by UID"() {
		when:
		ComicCollectionV2FullResponse comicCollectionV2FullResponse = stapiRestClient.comicCollection.getV2('CLMA0000108975')

		then:
		comicCollectionV2FullResponse.comicCollection.title == 'Convergence'
		comicCollectionV2FullResponse.comicCollection.comics.size() >= 3
	}

	void "'Star Trek Ultimate Edition' is among collections with more than 500 pages"() {
		given:
		ComicCollectionSearchCriteria comicCollectionSearchCriteria = new ComicCollectionSearchCriteria(
				numberOfPagesFrom: 500
		)

		when:
		ComicCollectionBaseResponse comicCollectionBaseResponse = stapiRestClient.comicCollection.search(comicCollectionSearchCriteria)

		then:
		comicCollectionBaseResponse.comicCollections.stream()
				.anyMatch { it -> it.title == 'Star Trek Ultimate Edition' }
	}

}
