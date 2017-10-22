package com.cezarykluczynski.stapi.server.occupation.query

import com.cezarykluczynski.stapi.model.occupation.dto.OccupationRequestDTO
import com.cezarykluczynski.stapi.model.occupation.repository.OccupationRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.occupation.dto.OccupationRestBeanParams
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class OccupationRestQueryTest extends Specification {

	private OccupationBaseRestMapper occupationRestMapperMock

	private PageMapper pageMapperMock

	private OccupationRepository occupationRepositoryMock

	private OccupationRestQuery occupationRestQuery

	void setup() {
		occupationRestMapperMock = Mock()
		pageMapperMock = Mock()
		occupationRepositoryMock = Mock()
		occupationRestQuery = new OccupationRestQuery(occupationRestMapperMock, pageMapperMock, occupationRepositoryMock)
	}

	void "maps OccupationRestBeanParams to OccupationRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		OccupationRestBeanParams occupationRestBeanParams = Mock()
		OccupationRequestDTO occupationRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = occupationRestQuery.query(occupationRestBeanParams)

		then:
		1 * occupationRestMapperMock.mapBase(occupationRestBeanParams) >> occupationRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(occupationRestBeanParams) >> pageRequest
		1 * occupationRepositoryMock.findMatching(occupationRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
