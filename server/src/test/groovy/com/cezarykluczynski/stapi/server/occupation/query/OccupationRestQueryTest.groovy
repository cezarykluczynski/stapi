package com.cezarykluczynski.stapi.server.occupation.query

import com.cezarykluczynski.stapi.model.occupation.dto.OccupationRequestDTO
import com.cezarykluczynski.stapi.model.occupation.repository.OccupationRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.occupation.dto.OccupationRestBeanParams
import com.cezarykluczynski.stapi.server.occupation.dto.OccupationV2RestBeanParams
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class OccupationRestQueryTest extends Specification {

	private OccupationBaseRestMapper occupationBaseRestMapperMock

	private PageMapper pageMapperMock

	private OccupationRepository occupationRepositoryMock

	private OccupationRestQuery occupationRestQuery

	void setup() {
		occupationBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		occupationRepositoryMock = Mock()
		occupationRestQuery = new OccupationRestQuery(occupationBaseRestMapperMock, pageMapperMock, occupationRepositoryMock)
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
		1 * occupationBaseRestMapperMock.mapBase(occupationRestBeanParams) >> occupationRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(occupationRestBeanParams) >> pageRequest
		1 * occupationRepositoryMock.findMatching(occupationRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps OccupationV2RestBeanParams to OccupationRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		OccupationV2RestBeanParams occupationV2RestBeanParams = Mock()
		OccupationRequestDTO occupationRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = occupationRestQuery.query(occupationV2RestBeanParams)

		then:
		1 * occupationBaseRestMapperMock.mapV2Base(occupationV2RestBeanParams) >> occupationRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(occupationV2RestBeanParams) >> pageRequest
		1 * occupationRepositoryMock.findMatching(occupationRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
