package com.cezarykluczynski.stapi.server.comic_series.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_COMIC_SERIES)
})
class ComicSeriesSoapEndpointIntegrationTest extends AbstractComicSeriesEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets comics by UID"() {
		when:
		ComicSeriesFullResponse comicSeriesBaseResponse = stapiSoapClient.comicSeriesPortType.getComicSeriesFull(new ComicSeriesFullRequest(
				uid: 'CSMA0000125094'
		))

		then:
		comicSeriesBaseResponse.comicSeries.title == 'Star Trek: Captain\'s Log'
	}

	void "gets the only comic series published during the year of original airing of TNG, that also have 'The Next Generation' in it's title"() {
		when:
		ComicSeriesBaseResponse comicSeriesBaseResponse = stapiSoapClient.comicSeriesPortType.getComicSeriesBase(new ComicSeriesBaseRequest(
				publishedYear: new IntegerRange(
						from: 1987,
						to: 1994
				),
				title: 'The Next Generation'
		))

		then:
		comicSeriesBaseResponse.comicSeries.size() == 1
		comicSeriesBaseResponse.comicSeries[0].title == 'Star Trek: The Next Generation (DC volume 1)'
	}

}
