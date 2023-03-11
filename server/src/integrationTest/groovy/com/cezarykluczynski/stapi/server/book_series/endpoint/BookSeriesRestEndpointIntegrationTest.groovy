package com.cezarykluczynski.stapi.server.book_series.endpoint

import com.cezarykluczynski.stapi.client.api.dto.BookSeriesSearchCriteria
import com.cezarykluczynski.stapi.client.rest.model.BookSeriesBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.BookSeriesFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_BOOK_SERIES)
})
class BookSeriesRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets book series by UID"() {
		when:
		BookSeriesFullResponse bookSeriesFullResponse = stapiRestClient.bookSeries.get('BSMA0000083432')

		then:
		bookSeriesFullResponse.bookSeries.title == 'Star Trek: Destiny'
	}

	void "Which Way Books is among book series with 2 to 5 books, published between 1980 and 1990"() {
		given:
		BookSeriesSearchCriteria bookSeriesSearchCriteria = new BookSeriesSearchCriteria(
				publishedYearFrom: 1980,
				publishedYearTo: 1990,
				numberOfBooksFrom: 2,
				numberOfBooksTo: 5
		)

		when:
		BookSeriesBaseResponse bookSeriesBaseResponse = stapiRestClient.bookSeries.search(bookSeriesSearchCriteria)

		then:
		bookSeriesBaseResponse.bookSeries.stream()
				.anyMatch { it.title == 'Which Way Books' }
	}

}
