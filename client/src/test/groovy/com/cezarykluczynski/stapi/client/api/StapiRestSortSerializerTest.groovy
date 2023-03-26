package com.cezarykluczynski.stapi.client.api

import com.cezarykluczynski.stapi.client.rest.model.RequestSort
import com.cezarykluczynski.stapi.client.rest.model.RequestSortClause
import com.cezarykluczynski.stapi.client.rest.model.RequestSortDirection
import spock.lang.Specification

class StapiRestSortSerializerTest extends Specification {

	private static final String NAME_1 = 'NAME_1'
	private static final RequestSortDirection SORT_ORDER_1 = RequestSortDirection.ASC
	private static final String NAME_2 = 'NAME_2'
	private static final RequestSortDirection SORT_ORDER_2 = RequestSortDirection.DESC

	void "serializes to string"() {
		given:
		RequestSort requestSort = new RequestSort(clauses: [
				new RequestSortClause(name: NAME_1, direction: SORT_ORDER_1, clauseOrder: 0),
				new RequestSortClause(name: NAME_2, direction: SORT_ORDER_2, clauseOrder: 1)
		])

		when:
		String sort = StapiRestSortSerializer.serialize(requestSort)

		then:
		sort == "${NAME_1},${SORT_ORDER_1.name()};${NAME_2},${SORT_ORDER_2.name()}".toString()
	}

}
