package com.cezarykluczynski.stapi.server.comic_strip.endpoint

import com.cezarykluczynski.stapi.client.rest.model.ComicStripBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.ComicStripFullResponse
import com.cezarykluczynski.stapi.client.rest.model.ComicStripSearchCriteria
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_COMIC_STRIPS)
})
class ComicStripRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets comic strip by UID"() {
		when:
		ComicStripFullResponse comicStripFullResponse = stapiRestClient.comicStrip.get('CTMA0000056047')

		then:
		comicStripFullResponse.comicStrip.title == 'Called Home'
	}

	void "finds comic strips by title"() {
		given:
		ComicStripSearchCriteria comicStripSearchCriteria = new ComicStripSearchCriteria(
				title: 'Aberration on Abaris'
		)

		when:
		ComicStripBaseResponse comicStripBaseResponse = stapiRestClient.comicStrip.search(comicStripSearchCriteria)

		then:
		comicStripBaseResponse.comicStrips[0].uid == 'CTMA0000056090'
	}

}
