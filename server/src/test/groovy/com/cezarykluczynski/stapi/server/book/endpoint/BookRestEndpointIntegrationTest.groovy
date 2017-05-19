package com.cezarykluczynski.stapi.server.book.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.BookFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.BookBaseResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_BOOKS)
})
class BookRestEndpointIntegrationTest extends AbstractBookEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets book by UID"() {
		when:
		BookFullResponse bookFullResponse = stapiRestClient.bookApi.bookGet('BOMA0000009513', null)

		then:
		bookFullResponse.book.title == 'The Four Years War'
	}

	void "Fatal Error is among eBooks published in or before 2000"() {
		when:
		BookBaseResponse bookBaseResponse = stapiRestClient.bookApi.bookSearchPost(null, null, null, null, null, null, 2000, null, null, null, null,
				null, null, null, null, null, null, true, null, null, null, null, null, null, null, null)

		then:
		bookBaseResponse.books.stream()
				.anyMatch { it.title == 'Fatal Error' }
	}

}
