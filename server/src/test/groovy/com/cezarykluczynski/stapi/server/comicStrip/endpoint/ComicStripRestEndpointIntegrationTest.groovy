package com.cezarykluczynski.stapi.server.comicStrip.endpoint

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

	void "gets comic strip by GUID"() {
		when:
		ComicStripFullResponse comicStripFullResponse = stapiRestClient.comicStripApi.comicStripGet('CTMA0000056047')

		then:
		comicStripFullResponse.comicStrip.title == 'Called Home'
	}

	void "finds comic strips by title"() {
		when:
		ComicStripBaseResponse comicStripBaseResponse = stapiRestClient.comicStripApi.comicStripSearchPost(0, 0, null, 'Aberration on Abaris', null,
				null, null, null, null, null)

		then:
		comicStripBaseResponse.comicStrips[0].guid == 'CTMA0000056090'
	}

}
