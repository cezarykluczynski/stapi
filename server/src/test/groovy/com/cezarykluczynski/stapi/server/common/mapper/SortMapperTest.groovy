package com.cezarykluczynski.stapi.server.common.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSortDirection
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.RequestSortClause
import com.cezarykluczynski.stapi.client.v1.soap.RequestSortDirectionEnum
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort as SoapResponseSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSortDirectionEnum
import org.assertj.core.util.Lists
import spock.lang.Specification

class SortMapperTest extends Specification {

	private static final String FIELD_1 = 'FIELD_1'
	private static final String FIELD_2 = 'FIELD_2'
	private static final String FIELD_3 = 'FIELD_3'
	private static final RequestSortDirectionEnum REQUEST_DIRECTION_ASC = RequestSortDirectionEnum.ASC
	private static final RequestSortDirectionEnum REQUEST_DIRECTION_DESC = RequestSortDirectionEnum.DESC
	private static final ResponseSortDirectionEnum RESPONSE_DIRECTION_ASC = ResponseSortDirectionEnum.ASC
	private static final ResponseSortDirectionEnum RESPONSE_DIRECTION_DESC = ResponseSortDirectionEnum.DESC

	private SortMapper sortMapper

	void setup() {
		sortMapper = new SortMapper()
	}

	void "maps RequestSort to SOAP ResponseSort"() {
		given:
		RequestSort requestSort = new RequestSort(clauses: Lists.newArrayList(
				new RequestSortClause(
						name: FIELD_3,
						direction: REQUEST_DIRECTION_ASC,
						clauseOrder: 2
				),
				new RequestSortClause(
						name: FIELD_1,
						direction: REQUEST_DIRECTION_DESC,
				),
				new RequestSortClause(
						name: FIELD_2,
						clauseOrder: 1
				)
		))

		when:
		SoapResponseSort responseSort = sortMapper.map(requestSort)

		then:
		responseSort.clauses[0].name == FIELD_1
		responseSort.clauses[0].direction == RESPONSE_DIRECTION_DESC
		responseSort.clauses[0].clauseOrder == 0
		responseSort.clauses[1].name == FIELD_2
		responseSort.clauses[1].direction == RESPONSE_DIRECTION_ASC
		responseSort.clauses[1].clauseOrder == 1
		responseSort.clauses[2].name == FIELD_3
		responseSort.clauses[2].direction == RESPONSE_DIRECTION_ASC
		responseSort.clauses[2].clauseOrder == 2
	}

	void "maps null to SOAP ResponseSort with empty clause list"() {
		when:
		SoapResponseSort responseSort = sortMapper.map((RequestSort) null)

		then:
		responseSort.clauses.empty
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
