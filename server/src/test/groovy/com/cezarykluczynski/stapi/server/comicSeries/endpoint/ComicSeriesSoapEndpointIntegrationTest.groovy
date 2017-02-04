package com.cezarykluczynski.stapi.server.comicSeries.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesResponse
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

	void "gets the only comic series published during the year of original airing of TNG, that also have 'The Next Generation' in it's title"() {
		when:
		ComicSeriesResponse comicSeriesResponse = stapiSoapClient.comicSeriesPortType.getComicSeries(new ComicSeriesRequest(
				publishedYear: new IntegerRange(
						from: 1987,
						to: 1994
				),
				title: 'The Next Generation'
		))

		then:
		comicSeriesResponse.comicSeries.size() == 1
		comicSeriesResponse.comicSeries[0].title == 'Star Trek: The Next Generation (DC volume 1)'
	}

}
