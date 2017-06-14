package com.cezarykluczynski.stapi.server.book_series.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_BOOK_SERIES)
})
class BookSeriesRestEndpointIntegrationTest extends AbstractBookSeriesEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets book series by UID"() {
		when:
		BookSeriesFullResponse bookSeriesFullResponse = stapiRestClient.bookSeriesApi.bookSeriesGet('BSMA0000083432', null)

		then:
		bookSeriesFullResponse.bookSeries.title == 'Star Trek: Destiny'
	}

	void "Which Way Books is among book series with 2 to 5 books, published between 1980 and 1990"() {
		when:
		BookSeriesBaseResponse bookSeriesBaseResponse = stapiRestClient.bookSeriesApi.bookSeriesSearchPost(null, null, null, null, null,
				1980, 1990, 2, 5, null, null, null, null)

		then:
		bookSeriesBaseResponse.bookSeries.stream()
				.anyMatch { it.title == 'Which Way Books' }
	}

}
