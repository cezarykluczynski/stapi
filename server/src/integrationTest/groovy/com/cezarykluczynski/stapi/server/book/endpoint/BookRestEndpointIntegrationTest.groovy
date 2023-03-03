package com.cezarykluczynski.stapi.server.book.endpoint

import com.cezarykluczynski.stapi.client.api.dto.BookV2SearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.model.BookV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.BookV2FullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_BOOKS)
})
class BookRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets book by UID"() {
		when:
		BookV2FullResponse bookV2FullResponse = stapiRestClient.book.getV2('BOMA0000009513')

		then:
		bookV2FullResponse.book.title == 'The Four Years War'
	}

	void "Fatal Error is among eBooks published in or before 2000"() {
		given:
		BookV2SearchCriteria bookV2SearchCriteria = new BookV2SearchCriteria()
		bookV2SearchCriteria.setEBook(true)
		bookV2SearchCriteria.setPublishedYearTo(2000)

		when:
		BookV2BaseResponse bookV2BaseResponse = stapiRestClient.book.searchV2(bookV2SearchCriteria)

		then:
		bookV2BaseResponse.books.stream()
				.anyMatch { it.title == 'Fatal Error' }
	}

}
