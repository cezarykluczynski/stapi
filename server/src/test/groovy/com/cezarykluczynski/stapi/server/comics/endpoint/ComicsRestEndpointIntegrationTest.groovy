package com.cezarykluczynski.stapi.server.comics.endpoint

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer
import com.cezarykluczynski.stapi.client.api.dto.RestSortClause
import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortDirection
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsResponse
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

	void "gets five issues of 'Ghosts'"() {
		when:
		ComicsResponse comicsResponse = stapiRestClient.comicsApi
				.comicsPost(0, 20, StapiRestSortSerializer.serialize(Lists.newArrayList(
				new RestSortClause(name: 'title', direction: RestSortDirection.ASC)
		)), null, 'Ghosts, Issue', null, null, null, null, null, null, null, null, null)

		then:
		comicsResponse.comics.size() == 5
		comicsResponse.comics[0].title == 'Ghosts, Issue 1'
		comicsResponse.comics[1].title == 'Ghosts, Issue 2'
		comicsResponse.comics[2].title == 'Ghosts, Issue 3'
		comicsResponse.comics[3].title == 'Ghosts, Issue 4'
		comicsResponse.comics[4].title == 'Ghosts, Issue 5'
	}

}
