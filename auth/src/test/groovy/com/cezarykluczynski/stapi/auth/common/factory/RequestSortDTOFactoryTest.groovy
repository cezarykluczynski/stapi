package com.cezarykluczynski.stapi.auth.common.factory

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.dto.enums.RequestSortDirectionDTO
import spock.lang.Specification

class RequestSortDTOFactoryTest extends Specification {

	private RequestSortDTOFactory requestSortDTOFactory

	void setup() {
		requestSortDTOFactory = new RequestSortDTOFactory()
	}

	void "creates RequestSortDTO"() {
		when:
		RequestSortDTO requestSortDTO = requestSortDTOFactory.create()

		then:
		requestSortDTO.clauses[0].name == 'id'
		requestSortDTO.clauses[0].direction == RequestSortDirectionDTO.ASC
	}

}
