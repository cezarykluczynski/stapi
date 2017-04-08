package com.cezarykluczynski.stapi.server.performer.query

import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class PerformerRestQueryTest extends Specification {

	private PerformerBaseRestMapper performerBaseRestMapperMock

	private PageMapper pageMapperMock

	private PerformerRepository performerRepositoryMock

	private PerformerRestQuery performerRestQuery

	void setup() {
		performerBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		performerRepositoryMock = Mock()
		performerRestQuery = new PerformerRestQuery(performerBaseRestMapperMock, pageMapperMock, performerRepositoryMock)
	}

	void "maps PerformerRestBeanParams to PerformerRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		PerformerRestBeanParams performerRestBeanParams = Mock()
		PerformerRequestDTO performerRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = performerRestQuery.query(performerRestBeanParams)

		then:
		1 * performerBaseRestMapperMock.mapBase(performerRestBeanParams) >> performerRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(performerRestBeanParams) >> pageRequest
		1 * performerRepositoryMock.findMatching(performerRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
