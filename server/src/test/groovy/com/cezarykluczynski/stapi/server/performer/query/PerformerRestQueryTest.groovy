package com.cezarykluczynski.stapi.server.performer.query

import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class PerformerRestQueryTest extends Specification {

	private PerformerRestMapper performerRestMapperMock

	private PageMapper pageMapperMock

	private PerformerRepository performerRepositoryMock

	private PerformerRestQuery performerRestQuery

	def setup() {
		performerRestMapperMock = Mock(PerformerRestMapper)
		pageMapperMock = Mock(PageMapper)
		performerRepositoryMock = Mock(PerformerRepository)
		performerRestQuery = new PerformerRestQuery(performerRestMapperMock, pageMapperMock,
				performerRepositoryMock)
	}

	def "maps PerformerRestBeanParams to PerformerRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock(PageRequest)
		PerformerRestBeanParams performerRestBeanParams = Mock(PerformerRestBeanParams) {

		}
		PerformerRequestDTO performerRequestDTO = Mock(PerformerRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = performerRestQuery.query(performerRestBeanParams)

		then:
		1 * performerRestMapperMock.map(performerRestBeanParams) >> performerRequestDTO
		1 * pageMapperMock.fromPageAwareBeanParamsToPageRequest(performerRestBeanParams) >> pageRequest
		1 * performerRepositoryMock.findMatching(performerRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
