package com.cezarykluczynski.stapi.server.book_collection.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.book.endpoint.AbstractBookEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_BOOK_COLLECTIONS)
})
class BookCollectionRestEndpointIntegrationTest extends AbstractBookEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets book collection by UID"() {
		when:
		BookCollectionFullResponse bookCollectionFullResponse = stapiRestClient.bookCollectionApi.bookCollectionGet('BCMA0000024329', null)

		then:
		bookCollectionFullResponse.bookCollection.title == 'Miracle Workers'
	}

	void "Star Trek: Odyssey is among book collections with more than 1000 pages"() {
		when:
		BookCollectionBaseResponse bookCollectionBaseResponse = stapiRestClient.bookCollectionApi.bookCollectionSearchPost(null, null, null, null,
				null, null, null, 1000, null, null , null, null, null)

		then:
		bookCollectionBaseResponse.bookCollections.stream()
				.anyMatch { it.title == 'Star Trek: Odyssey' }
	}

}
