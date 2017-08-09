package com.cezarykluczynski.stapi.server.soundtrack.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SoundtrackFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SOUNDTRACKS)
})
class SoundtrackRestEndpointIntegrationTest extends AbstractSoundtrackEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets soundtrack by UID"() {
		when:
		SoundtrackFullResponse soundtrackFullResponse = stapiRestClient.soundtrackApi.soundtrackGet('SOMA0000219730', null)

		then:
		soundtrackFullResponse.soundtrack.title == 'Star Trek: Voyager Collection'
	}

	void "'Broken Bow' is among soundtracks with 2900 to 3000 long"() {
		when:
		SoundtrackBaseResponse soundtrackBaseResponse = stapiRestClient.soundtrackApi.soundtrackSearchPost(null, null, null, null, null,
				null, null, 2900, 3000)

		then:
		soundtrackBaseResponse.soundtracks.stream()
				.anyMatch { it.title == 'Broken Bow' }
	}

}
