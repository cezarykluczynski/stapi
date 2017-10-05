package com.cezarykluczynski.stapi.server.book_collection.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.book.endpoint.AbstractBookEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_BOOK_COLLECTIONS)
})
class BookCollectionSoapEndpointIntegrationTest extends AbstractBookEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets book collection by UID"() {
		when:
		BookCollectionFullResponse bookCollectionFullResponse = stapiSoapClient.bookCollectionPortType
				.getBookCollectionFull(new BookCollectionFullRequest(
						uid: 'BCMA0000151821'
				))

		then:
		bookCollectionFullResponse.bookCollection.title == 'Star Trek: Destiny'
	}

	void 'Star Trek Log One, Log Two, Log Three is among collection published in 1992'() {
		when:
		BookCollectionBaseResponse bookCollectionBaseResponse = stapiSoapClient.bookCollectionPortType
				.getBookCollectionBase(new BookCollectionBaseRequest(
						publishedYear: new IntegerRange(from: 1992, to: 1992)
				))

		then:
		bookCollectionBaseResponse.bookCollections.stream()
				.anyMatch { it.title == 'Star Trek Log One, Log Two, Log Three' }
	}

}
