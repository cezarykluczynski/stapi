package com.cezarykluczynski.stapi.server.common.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSortDirection
import spock.lang.Specification

class SortMapperTest extends Specification {

	private SortMapper sortMapper

	void setup() {
		sortMapper = new SortMapper()
	}

	void "maps string to REST ResponseSort"() {
		when:
		ResponseSort responseSort = sortMapper.map('fieldName1,ASC;fieldName2,DESC;fieldName3,ASC')

		then:
		responseSort.clauses.size() == 3
		responseSort.clauses[0].name == 'fieldName1'
		responseSort.clauses[0].direction == ResponseSortDirection.ASC
		responseSort.clauses[1].name == 'fieldName2'
		responseSort.clauses[1].direction == ResponseSortDirection.DESC
		responseSort.clauses[2].name == 'fieldName3'
		responseSort.clauses[2].direction == ResponseSortDirection.ASC
	}

}
