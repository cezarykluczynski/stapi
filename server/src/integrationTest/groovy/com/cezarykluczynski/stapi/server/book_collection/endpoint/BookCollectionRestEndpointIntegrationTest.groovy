package com.cezarykluczynski.stapi.server.book_collection.endpoint

import com.cezarykluczynski.stapi.client.api.dto.BookCollectionSearchCriteria
import com.cezarykluczynski.stapi.client.rest.model.BookCollectionBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.BookCollectionFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_BOOK_COLLECTIONS)
})
class BookCollectionRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets book collection by UID"() {
		when:
		BookCollectionFullResponse bookCollectionFullResponse = stapiRestClient.bookCollection.get('BCMA0000024329')

		then:
		bookCollectionFullResponse.bookCollection.title == 'Miracle Workers'
	}

	void "Star Trek: Odyssey is among book collections with more than 1000 pages"() {
		given:
		BookCollectionSearchCriteria bookCollectionSearchCriteria = new BookCollectionSearchCriteria()
		bookCollectionSearchCriteria.setNumberOfPagesFrom(1000)

		when:
		BookCollectionBaseResponse bookCollectionBaseResponse = stapiRestClient.bookCollection.search(bookCollectionSearchCriteria)

		then:
		bookCollectionBaseResponse.bookCollections.stream()
				.anyMatch { it.title == 'Star Trek: Odyssey' }
	}

}
