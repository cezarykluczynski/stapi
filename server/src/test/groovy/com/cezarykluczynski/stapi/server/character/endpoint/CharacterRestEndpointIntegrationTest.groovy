package com.cezarykluczynski.stapi.server.character.endpoint

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer
import com.cezarykluczynski.stapi.client.api.dto.RestSortClause
import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortOrder
import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.google.common.collect.Lists
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_CHARACTERS)
})
class CharacterRestEndpointIntegrationTest extends AbstractCharacterEndpointIntegrationTest {

	def setup() {
		createRestClient()
	}

	def "gets character by guid"() {
		when:
		CharacterResponse characterResponse = stapiRestClient.characterApi.characterPost(null, null, null,
				DEANNA_TROI_GUID, null, null, null)

		then:
		characterResponse.page.totalElements == 1
		characterResponse.characters[0].guid == DEANNA_TROI_GUID
		characterResponse.characters[0].episodeHeaders.size() == 166
		characterResponse.characters[0].movieHeaders.size() == 4
	}

	def "gets characters sorted by year of birth"() {
		when:
		CharacterResponse characterResponse = stapiRestClient.characterApi.characterPost(null, null,
				StapiRestSortSerializer.serialize(Lists.newArrayList(
						new RestSortClause(name: 'yearOfBirth', sortOrder: RestSortOrder.ASC)
				)), null, null, null, null)

		then:
		characterResponse.characters[0].yearOfBirth == 1647
	}

}
