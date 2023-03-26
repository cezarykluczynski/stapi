package com.cezarykluczynski.stapi.server.character.endpoint

import com.cezarykluczynski.stapi.client.rest.model.CharacterBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.CharacterFullResponse
import com.cezarykluczynski.stapi.client.rest.model.CharacterSearchCriteria
import com.cezarykluczynski.stapi.client.rest.model.RequestSort
import com.cezarykluczynski.stapi.client.rest.model.RequestSortClause
import com.cezarykluczynski.stapi.client.rest.model.RequestSortDirection
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_CHARACTERS)
})
class CharacterRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets character by UID"() {
		when:
		CharacterFullResponse characterFullResponse = stapiRestClient.character.get('CHMA0000123101')

		then:
		characterFullResponse.character.name == 'Deanna Troi'
		characterFullResponse.character.movies.size() == 4
	}

	void "gets characters sorted by year of birth"() {
		given:
		CharacterSearchCriteria characterSearchCriteria = new CharacterSearchCriteria()
		characterSearchCriteria.setSort(new RequestSort().addClausesItem(new RequestSortClause(
				name: 'yearOfBirth', direction: RequestSortDirection.ASC, clauseOrder: 0)))

		when:
		CharacterBaseResponse characterBaseResponse = stapiRestClient.character.search(characterSearchCriteria)

		then:
		characterBaseResponse.characters[0].yearOfBirth == 1452
	}

}
