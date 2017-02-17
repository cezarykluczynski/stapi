package com.cezarykluczynski.stapi.server.comicSeries.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesResponse
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

	void "gets the only photoseries that is also a miniseries"() {
		when:
		ComicSeriesResponse comicSeriesResponse = stapiRestClient.comicSeriesApi
				.comicSeriesPost(0, 20, null, null, null, null, null, null, null, null, null, null, null, true, true)

		then:
		comicSeriesResponse.comicSeries.size() == 1
		comicSeriesResponse.comicSeries[0].title == 'Star Trek: New Visions'
	}

}
