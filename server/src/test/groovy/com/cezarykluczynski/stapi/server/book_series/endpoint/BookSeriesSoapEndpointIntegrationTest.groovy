package com.cezarykluczynski.stapi.server.book_series.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_BOOK_SERIES)
})
class BookSeriesSoapEndpointIntegrationTest extends AbstractBookSeriesEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets book series by UID"() {
		when:
		BookSeriesFullResponse bookSeriesFullResponse = stapiSoapClient.bookSeriesPortType.getBookSeriesFull(new BookSeriesFullRequest(
				uid: 'BSMA0000056896'
		))

		then:
		bookSeriesFullResponse.bookSeries.title == 'The Yesterday Saga'
	}

	void 'Star Trek: The Next Generation - Starfleet Academy is among miniseries with more than 10 books'() {
		when:
		BookSeriesBaseResponse bookSeriesBaseResponse = stapiSoapClient.bookSeriesPortType.getBookSeriesBase(new BookSeriesBaseRequest(
				miniseries: true,
				numberOfBooks: new IntegerRange(
						from: 10,
						to: null)))

		then:
		bookSeriesBaseResponse.bookSeries.stream()
				.anyMatch { it.title == 'Star Trek: The Next Generation - Starfleet Academy' }
	}

}
