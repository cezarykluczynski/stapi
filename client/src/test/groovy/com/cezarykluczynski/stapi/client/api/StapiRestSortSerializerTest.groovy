package com.cezarykluczynski.stapi.client.api

import com.cezarykluczynski.stapi.client.api.dto.RestSortClause
import com.cezarykluczynski.stapi.client.api.dto.enums.RestSortDirection
import com.google.common.collect.Lists
import spock.lang.Specification

class StapiRestSortSerializerTest extends Specification {

	private static final String NAME_1 = 'NAME_1'
	private static final RestSortDirection SORT_ORDER_1 = RestSortDirection.ASC
	private static final String NAME_2 = 'NAME_2'
	private static final RestSortDirection SORT_ORDER_2 = RestSortDirection.DESC

	void "serializes to string"() {
		given:
		List<RestSortClause> restSortClauseList = Lists.newArrayList(
				new RestSortClause(name: NAME_1, direction: SORT_ORDER_1),
				new RestSortClause(name: NAME_2, direction: SORT_ORDER_2)
		)

		when:
		String sort = StapiRestSortSerializer.serialize(restSortClauseList)

		then:
		sort == "${NAME_1},${SORT_ORDER_1.name()};${NAME_2},${SORT_ORDER_2.name()}".toString()
	}

}
