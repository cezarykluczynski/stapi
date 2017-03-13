package com.cezarykluczynski.stapi.server.comicStrip.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.RequestSortClause
import com.cezarykluczynski.stapi.client.v1.soap.RequestSortDirectionEnum
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.google.common.collect.Lists
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_COMIC_STRIPS)
})
class ComicStripSoapEndpointIntegrationTest extends AbstractComicStripEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets 'UK Story Arc' comic strips sorted by publication date"() {
		when:
		ComicStripResponse comicStripResponse = stapiSoapClient.comicStripPortType.getComicStrips(new ComicStripRequest(
				title: 'UK Story Arc',
				sort: new RequestSort(
						clauses: Lists.newArrayList(
								new RequestSortClause(
										name: 'publishedYearFrom',
										direction: RequestSortDirectionEnum.ASC
								),
								new RequestSortClause(
										name: 'publishedMonthFrom',
										direction: RequestSortDirectionEnum.ASC
								),
								new RequestSortClause(
										name: 'publishedDayFrom',
										direction: RequestSortDirectionEnum.ASC
								)))))

		then:
		comicStripResponse.comicStrip.size() == 37
		comicStripResponse.comicStrip[0].title == 'First UK Story Arc'
		comicStripResponse.comicStrip[36].title == 'Thirty-Seventh UK Story Arc'
	}

}
