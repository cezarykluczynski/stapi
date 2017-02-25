package com.cezarykluczynski.stapi.server.comicCollection.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicCollectionResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_COMICS) &&
			StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_COMIC_COLLECTIONS)
})
class ComicCollectionRestEndpointIntegrationTest extends AbstractComicCollectionEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets comic collection by guid"() {
		when:
		ComicCollectionResponse collectionResponse = stapiRestClient.comicCollectionApi
				.comicCollectionPost(null, null, null, 'CLMA0000108975', null, null, null, null, null, null, null, null, null, null)

		then:
		collectionResponse.comicCollections.size() == 1
		collectionResponse.comicCollections[0].title == 'Convergence'
		collectionResponse.comicCollections[0].comicsHeaders.size() >= 3
	}

}
