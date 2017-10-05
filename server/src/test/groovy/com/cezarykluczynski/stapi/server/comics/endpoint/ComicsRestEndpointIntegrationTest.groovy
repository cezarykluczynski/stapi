package com.cezarykluczynski.stapi.server.comics.endpoint

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer
import com.cezarykluczynski.stapi.client.api.dto.RestSortClause
import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortDirection
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.google.common.collect.Lists
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_COMICS)
})
class ComicsRestEndpointIntegrationTest extends AbstractComicsEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets comics by UID"() {
		when:
		ComicsFullResponse comicsFullResponse = stapiRestClient.comicsApi.comicsGet('CCMA0000054566', null)

		then:
		comicsFullResponse.comics.title == 'Day of the Inquisitors'
	}

	void "gets five issues of 'Ghosts'"() {
		when:
		ComicsBaseResponse comicsBaseResponse = stapiRestClient.comicsApi
				.comicsSearchPost(0, 20, StapiRestSortSerializer.serialize(Lists.newArrayList(
				new RestSortClause(name: 'title', direction: RestSortDirection.ASC)
		)), null, 'Ghosts, Issue', null, null, null, null, null, null, null, null, null, null)

		then:
		comicsBaseResponse.comics.size() == 5
		comicsBaseResponse.comics[0].title == 'Ghosts, Issue 1'
		comicsBaseResponse.comics[1].title == 'Ghosts, Issue 2'
		comicsBaseResponse.comics[2].title == 'Ghosts, Issue 3'
		comicsBaseResponse.comics[3].title == 'Ghosts, Issue 4'
		comicsBaseResponse.comics[4].title == 'Ghosts, Issue 5'
	}

}
