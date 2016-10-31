package com.cezarykluczynski.stapi.server.performer.query

import com.cezarykluczynski.stapi.client.soap.PerformerRequest
import com.cezarykluczynski.stapi.client.soap.RequestPage
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerRequestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class PerformerQueryBuilderTest extends Specification {

	private PerformerRequestMapper performerRequestMapperMock

	private PageMapper pageMapperMock

	private PerformerRepository performerRepositoryMock

	private PerformerQueryBuilder performerQueryBuilder

	def setup() {
		performerRequestMapperMock = Mock(PerformerRequestMapper)
		pageMapperMock = Mock(PageMapper)
		performerRepositoryMock = Mock(PerformerRepository)
		performerQueryBuilder = new PerformerQueryBuilder(performerRequestMapperMock, pageMapperMock,
				performerRepositoryMock)
	}

	def "maps PerformerRequest to PerformerRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)
		PerformerRequest performerRequest = Mock(PerformerRequest) {
			getPage() >> requestPage
		}
		PerformerRequestDTO performerRequestDTO = Mock(PerformerRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = performerQueryBuilder.query(performerRequest)

		then:
		1 * performerRequestMapperMock.map(performerRequest) >> performerRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * performerRepositoryMock.findMatching(performerRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	def "maps PerformerRestBeanParams to PerformerRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock(PageRequest)
		PerformerRestBeanParams performerRestBeanParams = Mock(PerformerRestBeanParams) {

		}
		PerformerRequestDTO performerRequestDTO = Mock(PerformerRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = performerQueryBuilder.query(performerRestBeanParams)

		then:
		1 * performerRequestMapperMock.map(performerRestBeanParams) >> performerRequestDTO
		1 * pageMapperMock.fromPageAwareBeanParamsToPageRequest(performerRestBeanParams) >> pageRequest
		1 * performerRepositoryMock.findMatching(performerRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
