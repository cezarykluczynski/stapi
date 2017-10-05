package com.cezarykluczynski.stapi.server.book.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.BookBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.BookFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.BookFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_BOOKS)
})
class BookSoapEndpointIntegrationTest extends AbstractBookEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets book by UID"() {
		when:
		BookFullResponse bookFullResponse = stapiSoapClient.bookPortType.getBookFull(new BookFullRequest(
				uid: 'BOMA0000097110'
		))

		then:
		bookFullResponse.book.title == 'Graduation Exercise'
	}

	void 'Klingon Ship Recognition Manual is among role playing books with \'Klingon\' in title'() {
		when:
		BookBaseResponse bookBaseResponse = stapiSoapClient.bookPortType.getBookBase(new BookBaseRequest(
				title: 'Klingon',
				rolePlayingBook: true))

		then:
		bookBaseResponse.books.stream()
				.anyMatch { it.title == 'Klingon Ship Recognition Manual' }
	}

}
