package com.cezarykluczynski.stapi.server.character.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.GenderEnum
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_CHARACTERS)
})
class CharacterSoapEndpointIntegrationTest extends AbstractCharacterEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets character by guid"() {
		when:
		CharacterFullResponse characterFullResponse = stapiSoapClient.characterPortType.getCharacterFull(new CharacterFullRequest(
				guid: DEANNA_TROI_GUID
		))

		then:
		characterFullResponse.character.guid == DEANNA_TROI_GUID
		characterFullResponse.character.episodes.size() == 166
		characterFullResponse.character.movies.size() == 4
	}

	@SuppressWarnings('ClosureAsLastMethodParameter')
	void "find deceased mirror females"() {
		when:
		CharacterBaseResponse characterBaseResponse = stapiSoapClient.characterPortType.getCharacterBase(new CharacterBaseRequest(
				gender: GenderEnum.F,
				deceased: true,
				mirror: true
		))

		then:
		characterBaseResponse.characters.stream().anyMatch({ it -> it.name == 'Jennifer Sisko' })
		characterBaseResponse.characters.stream().anyMatch({ it -> it.name == 'Jadzia Dax' })
	}

}
