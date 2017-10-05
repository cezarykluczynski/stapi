package com.cezarykluczynski.stapi.server.comic_strip.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullResponse
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

	void "gets comic strip by UID"() {
		when:
		ComicStripFullResponse comicStripFullResponse = stapiSoapClient.comicStripPortType.getComicStripFull(new ComicStripFullRequest(
				uid: 'CTMA0000088263'
		))

		then:
		comicStripFullResponse.comicStrip.title == 'Sixth UK Annual Story'
	}

	void "gets 'UK Story Arc' comic strips sorted by publication date"() {
		when:
		ComicStripBaseResponse comicStripResponse = stapiSoapClient.comicStripPortType.getComicStripBase(new ComicStripBaseRequest(
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
		comicStripResponse.comicStrips[0].title == 'First UK Story Arc'
		comicStripResponse.comicStrips[36].title == 'Thirty-Seventh UK Story Arc'
	}

}
