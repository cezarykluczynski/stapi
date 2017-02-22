package com.cezarykluczynski.stapi.server.comicStrip.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicStripResponse
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
		ComicStripResponse comicStripResponse = stapiRestClient.comicStripApi.comicStripPost(null, null, null, 'CTMA0000056047', null, null, null,
			null, null, null, null)

		then:
		comicStripResponse.comicStrips.size() == 1
		comicStripResponse.comicStrips[0].title == 'Called Home'
	}

}
