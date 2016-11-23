package com.cezarykluczynski.stapi.server.character.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.CharacterRequest
import com.cezarykluczynski.stapi.client.v1.soap.CharacterResponse

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
