package com.cezarykluczynski.stapi.server.common.mapper

import com.cezarykluczynski.stapi.client.v1.soap.RequestOrder
import com.cezarykluczynski.stapi.client.v1.soap.RequestOrderClause
import com.cezarykluczynski.stapi.client.v1.soap.RequestOrderEnum
import com.cezarykluczynski.stapi.model.common.dto.RequestOrderDTO
import com.cezarykluczynski.stapi.model.common.dto.enums.RequestOrderEnumDTO
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class RequestOrderMapperTest extends Specification {

	private static final String NAME_1 = 'NAME_1'
	private static final RequestOrderEnum SOAP_ORDER_1 = RequestOrderEnum.ASC
	private static final RequestOrderEnumDTO ORDER_1 = RequestOrderEnumDTO.ASC
	private static final Integer CLAUSE_ORDER_1 = 1
	private static final String NAME_2 = 'NAME_2'
	private static final RequestOrderEnum SOAP_ORDER_2 = RequestOrderEnum.DESC
	private static final RequestOrderEnumDTO ORDER_2 = RequestOrderEnumDTO.DESC
	private static final Integer CLAUSE_ORDER_2 = 2

	private RequestOrderMapper requestOrderMapper

	def setup() {
		requestOrderMapper = Mappers.getMapper(RequestOrderMapper)
	}

	def "maps SOAP RequestOrder to RequestOrderDTO"() {
		given:
		RequestOrder requestOrder = new RequestOrder(
				clauses: Lists.newArrayList(
						new RequestOrderClause(
								name: NAME_1,
								order: SOAP_ORDER_1,
								clauseOrder: CLAUSE_ORDER_1
						),
						new RequestOrderClause(
								name: NAME_2,
								order: SOAP_ORDER_2,
								clauseOrder: CLAUSE_ORDER_2
						)
				)
		)

		when:
		RequestOrderDTO requestOrderDTO = requestOrderMapper.mapRequestOrder(requestOrder)

		then:
		requestOrderDTO.clauses[0].name == NAME_1
		requestOrderDTO.clauses[0].order == ORDER_1
		requestOrderDTO.clauses[0].clauseOrder == CLAUSE_ORDER_1
		requestOrderDTO.clauses[1].name == NAME_2
		requestOrderDTO.clauses[1].order == ORDER_2
		requestOrderDTO.clauses[1].clauseOrder == CLAUSE_ORDER_2
	}

}
