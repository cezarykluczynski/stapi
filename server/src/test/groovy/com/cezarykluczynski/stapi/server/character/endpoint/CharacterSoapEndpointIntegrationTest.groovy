package com.cezarykluczynski.stapi.server.character.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.CharacterRequest
import com.cezarykluczynski.stapi.client.v1.soap.CharacterResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_CHARACTERS)
})
class CharacterSoapEndpointIntegrationTest extends AbstractCharacterEndpointIntegrationTest {

	def setup() {
		createSoapClient()
	}

	def "gets character by guid"() {
		when:
		CharacterResponse characterResponse = stapiSoapClient.characterPortType.getCharacters(new CharacterRequest(
				guid: DEANNA_TROI_GUID
		))

		then:
		characterResponse.page.totalElements == 1
		characterResponse.characters[0].guid == DEANNA_TROI_GUID
	}

}
