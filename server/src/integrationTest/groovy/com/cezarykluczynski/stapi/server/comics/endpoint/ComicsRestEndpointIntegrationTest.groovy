package com.cezarykluczynski.stapi.server.comics.endpoint

import com.cezarykluczynski.stapi.client.rest.model.ComicsBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.ComicsFullResponse
import com.cezarykluczynski.stapi.client.rest.model.ComicsSearchCriteria
import com.cezarykluczynski.stapi.client.rest.model.RequestSort
import com.cezarykluczynski.stapi.client.rest.model.RequestSortClause
import com.cezarykluczynski.stapi.client.rest.model.RequestSortDirection
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_COMICS)
})
class ComicsRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets comics by UID"() {
		when:
		ComicsFullResponse comicsFullResponse = stapiRestClient.comics.get('CCMA0000054566')

		then:
		comicsFullResponse.comics.title == 'Day of the Inquisitors'
	}

	void "gets five issues of 'Ghosts'"() {
		given:
		ComicsSearchCriteria comicsSearchCriteria = new ComicsSearchCriteria(
				title: 'Ghosts, Issue'
		)
		comicsSearchCriteria.setSort(new RequestSort().addClausesItem(new RequestSortClause(
				name: 'title', direction: RequestSortDirection.ASC, clauseOrder: 0)))

		when:
		ComicsBaseResponse comicsBaseResponse = stapiRestClient.comics.search(comicsSearchCriteria)

		then:
		comicsBaseResponse.comics.size() == 5
		comicsBaseResponse.comics[0].title == 'Ghosts, Issue 1'
		comicsBaseResponse.comics[1].title == 'Ghosts, Issue 2'
		comicsBaseResponse.comics[2].title == 'Ghosts, Issue 3'
		comicsBaseResponse.comics[3].title == 'Ghosts, Issue 4'
		comicsBaseResponse.comics[4].title == 'Ghosts, Issue 5'
	}

}
