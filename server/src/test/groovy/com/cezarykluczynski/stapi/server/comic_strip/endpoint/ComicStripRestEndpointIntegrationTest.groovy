package com.cezarykluczynski.stapi.server.comic_strip.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_COMIC_STRIPS)
})
class ComicStripRestEndpointIntegrationTest extends AbstractComicStripEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets comic strip by UID"() {
		when:
		ComicStripFullResponse comicStripFullResponse = stapiRestClient.comicStripApi.comicStripGet('CTMA0000056047', null)

		then:
		comicStripFullResponse.comicStrip.title == 'Called Home'
	}

	void "finds comic strips by title"() {
		when:
		ComicStripBaseResponse comicStripBaseResponse = stapiRestClient.comicStripApi.comicStripSearchPost(0, 0, null, null,  'Aberration on Abaris',
				null, null, null, null, null, null)

		then:
		comicStripBaseResponse.comicStrips[0].uid == 'CTMA0000056090'
	}

}
