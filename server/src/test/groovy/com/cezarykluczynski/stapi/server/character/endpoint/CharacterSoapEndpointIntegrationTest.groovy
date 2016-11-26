package com.cezarykluczynski.stapi.server.character.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.CharacterRequest
import com.cezarykluczynski.stapi.client.v1.soap.CharacterResponse
import com.cezarykluczynski.stapi.etl.common.service.JobCompletenessDecider
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(JobCompletenessDecider.STEP_004_CREATE_CHARACTERS)
})
class CharacterSoapEndpointIntegrationTest extends AbstractCharacterEndpointIntegrationTest {

	def setup() {
		createSoapClient()
	}

	def "gets character by guid"() {
		when:
		CharacterResponse characterResponse = stapiSoapClient.characterPortType.getCharacters(new CharacterRequest(
				guid: GUID
		))

		then:
		characterResponse.page.totalElements == 1
		characterResponse.characters[0].guid == GUID
	}

}
