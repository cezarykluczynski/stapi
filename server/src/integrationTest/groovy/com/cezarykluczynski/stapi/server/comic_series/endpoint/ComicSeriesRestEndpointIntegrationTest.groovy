package com.cezarykluczynski.stapi.server.comic_series.endpoint

import com.cezarykluczynski.stapi.client.api.dto.ComicSeriesSearchCriteria
import com.cezarykluczynski.stapi.client.rest.model.ComicSeriesBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.ComicSeriesFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_COMIC_SERIES)
})
class ComicSeriesRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets comic series by UID"() {
		when:
		ComicSeriesFullResponse comicSeriesFullResponse = stapiRestClient.comicSeries.get('CSMA0000157262')

		then:
		comicSeriesFullResponse.comicSeries.title == 'Star Trek Classics'
	}

	void "gets the only photonovel series that is also a miniseries"() {
		ComicSeriesSearchCriteria comicSeriesSearchCriteria = new ComicSeriesSearchCriteria(
				photonovelSeries: true,
				miniseries: true
		)

		when:
		ComicSeriesBaseResponse comicSeriesBaseResponse = stapiRestClient.comicSeries.search(comicSeriesSearchCriteria)

		then:
		comicSeriesBaseResponse.comicSeries.size() == 1
		comicSeriesBaseResponse.comicSeries[0].title == 'Star Trek: New Visions'
	}

}
