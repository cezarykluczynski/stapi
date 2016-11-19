package com.cezarykluczynski.stapi.server.series.query

import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams
import com.cezarykluczynski.stapi.server.series.mapper.SeriesRequestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class SeriesRestQueryTest extends Specification {

	private SeriesRequestMapper seriesRequestMapperMock

	private PageMapper pageMapperMock

	private SeriesRepository seriesRepositoryMock

	private SeriesRestQuery seriesRestQuery

	def setup() {
		seriesRequestMapperMock = Mock(SeriesRequestMapper)
		pageMapperMock = Mock(PageMapper)
		seriesRepositoryMock = Mock(SeriesRepository)
		seriesRestQuery = new SeriesRestQuery(seriesRequestMapperMock, pageMapperMock,
				seriesRepositoryMock)
	}

	def "maps SeriesRestBeanParams to SeriesRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock(PageRequest)
		SeriesRestBeanParams seriesRestBeanParams = Mock(SeriesRestBeanParams) {

		}
		SeriesRequestDTO seriesRequestDTO = Mock(SeriesRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = seriesRestQuery.query(seriesRestBeanParams)

		then:
		1 * seriesRequestMapperMock.map(seriesRestBeanParams) >> seriesRequestDTO
		1 * pageMapperMock.fromPageAwareBeanParamsToPageRequest(seriesRestBeanParams) >> pageRequest
		1 * seriesRepositoryMock.findMatching(seriesRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
