package com.cezarykluczynski.stapi.server.common.mapper

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import org.mapstruct.factory.Mappers

class RequestSortRestMapperTest extends AbstractRequestSortMapperTest {

	private RequestSortRestMapper requestSortRestMapper

	void setup() {
		requestSortRestMapper = Mappers.getMapper(RequestSortRestMapper)
	}

	void "maps valid string RequestSortDTO"() {
		when:
		RequestSortDTO requestSortDTO = requestSortRestMapper.mapString('abc,ASC')

		then:
		requestSortDTO.clauses.size() == 1
	}

	void "maps null string to RequestSortDTO with empty list of clauses"() {
		when:
		RequestSortDTO requestSortDTO = requestSortRestMapper.mapString(null)

		then:
		requestSortDTO.clauses.empty
	}

	void "maps empty string to RequestSortDTO with empty list of clauses"() {
		when:
		RequestSortDTO requestSortDTO = requestSortRestMapper.mapString('')

		then:
		requestSortDTO.clauses.empty
	}

	void "maps string to RequestSortDTO"() {
		given:
		String sort = "${NAME_1},${SORT_DIRECTION_1.name()};${NAME_2},${SORT_DIRECTION_2.name()}"

		when:
		RequestSortDTO requestSortDTO = requestSortRestMapper.mapString(sort)

		then:
		requestSortDTO.clauses.size() == 2
		requestSortDTO.clauses[0].name == NAME_1
		requestSortDTO.clauses[0].direction == SORT_DIRECTION_1
		requestSortDTO.clauses[0].clauseOrder == 0
		requestSortDTO.clauses[1].name == NAME_2
		requestSortDTO.clauses[1].direction == SORT_DIRECTION_2
		requestSortDTO.clauses[1].clauseOrder == 1
	}

	void "throws exception for uncomplete rule"() {
		when:
		requestSortRestMapper.mapString(NAME_1)

		then:
		thrown(RuntimeException)
	}

	void "throws exception when order is neither ASC nor DESC"() {
		given:
		String sort = "${NAME_1},${NAME_1}"

		when:
		requestSortRestMapper.mapString(sort)

		then:
		thrown(RuntimeException)
	}

	void "throws exception when field name is empty"() {
		given:
		String sort = ",${SORT_DIRECTION_1}"

		when:
		requestSortRestMapper.mapString(sort)

		then:
		thrown(RuntimeException)
	}

}
