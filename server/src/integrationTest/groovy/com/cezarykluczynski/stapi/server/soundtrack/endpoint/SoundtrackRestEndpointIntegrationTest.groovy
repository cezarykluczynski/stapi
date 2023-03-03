package com.cezarykluczynski.stapi.server.soundtrack.endpoint

import com.cezarykluczynski.stapi.client.api.dto.SoundtrackSearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SOUNDTRACKS)
})
class SoundtrackRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets soundtrack by UID"() {
		when:
		SoundtrackFullResponse soundtrackFullResponse = stapiRestClient.soundtrack.get('SOMA0000219730')

		then:
		soundtrackFullResponse.soundtrack.title == 'Star Trek: Voyager Collection'
	}

	void "'Broken Bow' is among soundtracks with 2900 to 3000 long"() {
		given:
		SoundtrackSearchCriteria soundtrackSearchCriteria = new SoundtrackSearchCriteria(
				lengthFrom: 2900,
				lengthTo: 3000
		)

		when:
		SoundtrackBaseResponse soundtrackBaseResponse = stapiRestClient.soundtrack.search(soundtrackSearchCriteria)

		then:
		soundtrackBaseResponse.soundtracks.stream()
				.anyMatch { it.title == 'Broken Bow' }
	}

}
