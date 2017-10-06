package com.cezarykluczynski.stapi.server.character.endpoint

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer
import com.cezarykluczynski.stapi.client.api.dto.RestSortClause
import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortDirection
import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.google.common.collect.Lists
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_CHARACTERS)
})
class CharacterRestEndpointIntegrationTest extends AbstractCharacterEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets character by uid"() {
		when:
		CharacterFullResponse characterFullResponse = stapiRestClient.characterApi.characterGet(DEANNA_TROI_UID, null)

		then:
		characterFullResponse.character.uid == DEANNA_TROI_UID
		characterFullResponse.character.movies.size() == 4
	}

	void "gets characters sorted by year of birth"() {
		when:
		CharacterBaseResponse characterBaseResponse = stapiRestClient.characterApi.characterSearchPost(null, null,
				StapiRestSortSerializer.serialize(Lists.newArrayList(
						new RestSortClause(name: 'yearOfBirth', direction: RestSortDirection.ASC)
				)), null, null, null, null, null, null, null, null)

		then:
		characterBaseResponse.characters[0].yearOfBirth == 1647
	}

}
