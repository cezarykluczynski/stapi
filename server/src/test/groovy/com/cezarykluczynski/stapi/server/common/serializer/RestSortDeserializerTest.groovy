package com.cezarykluczynski.stapi.server.common.serializer

import com.cezarykluczynski.stapi.model.common.dto.RequestSortClauseDTO
import com.cezarykluczynski.stapi.model.common.dto.enums.RequestSortDirectionDTO
import spock.lang.Specification

class RestSortDeserializerTest extends Specification {

	void "maps String to REST ResponseSort"() {
		when:
		List<RequestSortClauseDTO> clauses = RestSortDeserializer.deserialize('fieldName1,ASC;fieldName2,DESC;fieldName3,ASC')

		then:
		clauses[0].name == 'fieldName1'
		clauses[0].direction == RequestSortDirectionDTO.ASC
		clauses[0].clauseOrder == 0
		clauses[1].name == 'fieldName2'
		clauses[1].direction == RequestSortDirectionDTO.DESC
		clauses[1].clauseOrder == 1
		clauses[2].name == 'fieldName3'
		clauses[2].direction == RequestSortDirectionDTO.ASC
		clauses[2].clauseOrder == 2
	}

	void "maps null to REST ResponseSort with empty clause list"() {
		when:
		List<RequestSortClauseDTO> clauses = RestSortDeserializer.deserialize((String) null)

		then:
		clauses.empty
	}

}
