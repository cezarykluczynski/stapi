package com.cezarykluczynski.stapi.server.soundtrack.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBase
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.SoundtrackFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_SOUNDTRACKS)
})
class SoundtrackSoapEndpointIntegrationTest extends AbstractSoundtrackEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets soundtrack by UID"() {
		when:
		SoundtrackFullResponse soundtrackFullResponse = stapiSoapClient.soundtrackPortType.getSoundtrackFull(new SoundtrackFullRequest(
				uid: 'SOMA0000135387'
		))

		then:
		soundtrackFullResponse.soundtrack.title == 'Star Trek: The Deluxe Edition'
	}

	void "gets soundtrack by title"() {
		when:
		SoundtrackBaseResponse soundtrackBaseResponse = stapiSoapClient.soundtrackPortType.getSoundtrackBase(new SoundtrackBaseRequest(
				title: 'Encounter at Farpoint/The Arsenal of Freedom'
		))
		List<SoundtrackBase> soundtrackBaseList = soundtrackBaseResponse.soundtracks

		then:
		soundtrackBaseList.size() == 1
		soundtrackBaseList[0].uid == 'SOMA0000185593'
	}

}
