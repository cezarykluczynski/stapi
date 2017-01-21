package com.cezarykluczynski.stapi.server.common.mapper

import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.RequestSortClause
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class RequestSortSoapMapperTest extends AbstractRequestSortMapperTest {

	private RequestSortSoapMapper requestSortSoapMapper

	void setup() {
		requestSortSoapMapper = Mappers.getMapper(RequestSortSoapMapper)
	}

	void "maps SOAP RequestSort to RequestSortDTO"() {
		given:
		RequestSort requestSort = new RequestSort(
				clauses: Lists.newArrayList(
						new RequestSortClause(
								name: NAME_1,
								direction: SOAP_SORT_DIRECTION_1,
								clauseOrder: CLAUSE_ORDER_1
						),
						new RequestSortClause(
								name: NAME_2,
								direction: SOAP_SORT_DIRECTION_2,
								clauseOrder: CLAUSE_ORDER_2
						)
				)
		)

		when:
		RequestSortDTO requestSortDTO = requestSortSoapMapper.mapRequestSort(requestSort)

		then:
		requestSortDTO.clauses[0].name == NAME_1
		requestSortDTO.clauses[0].direction == SORT_DIRECTION_1
		requestSortDTO.clauses[0].clauseOrder == CLAUSE_ORDER_1
		requestSortDTO.clauses[1].name == NAME_2
		requestSortDTO.clauses[1].direction == SORT_DIRECTION_2
		requestSortDTO.clauses[1].clauseOrder == CLAUSE_ORDER_2
	}

}
