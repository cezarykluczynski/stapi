package com.cezarykluczynski.stapi.server.comic_series.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_COMIC_SERIES)
})
class ComicSeriesRestEndpointIntegrationTest extends AbstractComicSeriesEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets comic series by UID"() {
		when:
		ComicSeriesFullResponse comicSeriesFullResponse = stapiRestClient.comicSeriesApi.comicSeriesGet('CSMA0000157262', null)

		then:
		comicSeriesFullResponse.comicSeries.title == 'Star Trek Classics'
	}

	void "gets the only photoseries that is also a miniseries"() {
		when:
		ComicSeriesBaseResponse comicSeriesBaseResponse = stapiRestClient.comicSeriesApi
				.comicSeriesSearchPost(0, 20, null, null, null, null, null, null, null, null, null, null, null, true, true)

		then:
		comicSeriesBaseResponse.comicSeries.size() == 1
		comicSeriesBaseResponse.comicSeries[0].title == 'Star Trek: New Visions'
	}

}
