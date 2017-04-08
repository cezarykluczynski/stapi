package com.cezarykluczynski.stapi.server.series.query

import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams
import com.cezarykluczynski.stapi.server.series.mapper.SeriesBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class SeriesRestQueryTest extends Specification {

	private SeriesBaseRestMapper seriesBaseRestMapperMock

	private PageMapper pageMapperMock

	private SeriesRepository seriesRepositoryMock

	private SeriesRestQuery seriesRestQuery

	void setup() {
		seriesBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		seriesRepositoryMock = Mock()
		seriesRestQuery = new SeriesRestQuery(seriesBaseRestMapperMock, pageMapperMock, seriesRepositoryMock)
	}

	void "maps SeriesRestBeanParams to SeriesRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		SeriesRestBeanParams seriesRestBeanParams = Mock()
		SeriesRequestDTO seriesRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = seriesRestQuery.query(seriesRestBeanParams)

		then:
		1 * seriesBaseRestMapperMock.mapBase(seriesRestBeanParams) >> seriesRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(seriesRestBeanParams) >> pageRequest
		1 * seriesRepositoryMock.findMatching(seriesRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
