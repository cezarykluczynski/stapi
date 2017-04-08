package com.cezarykluczynski.stapi.server.comicSeries.query

import com.cezarykluczynski.stapi.model.comicSeries.dto.ComicSeriesRequestDTO
import com.cezarykluczynski.stapi.model.comicSeries.repository.ComicSeriesRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.comicSeries.dto.ComicSeriesRestBeanParams
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class ComicSeriesRestQueryTest extends Specification {

	private ComicSeriesBaseRestMapper comicSeriesRestMapperMock

	private PageMapper pageMapperMock

	private ComicSeriesRepository comicSeriesRepositoryMock

	private ComicSeriesRestQuery comicSeriesRestQuery

	void setup() {
		comicSeriesRestMapperMock = Mock()
		pageMapperMock = Mock()
		comicSeriesRepositoryMock = Mock()
		comicSeriesRestQuery = new ComicSeriesRestQuery(comicSeriesRestMapperMock, pageMapperMock, comicSeriesRepositoryMock)
	}

	void "maps ComicSeriesRestBeanParams to ComicSeriesRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		ComicSeriesRestBeanParams comicSeriesRestBeanParams = Mock()
		ComicSeriesRequestDTO comicSeriesRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = comicSeriesRestQuery.query(comicSeriesRestBeanParams)

		then:
		1 * comicSeriesRestMapperMock.mapBase(comicSeriesRestBeanParams) >> comicSeriesRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(comicSeriesRestBeanParams) >> pageRequest
		1 * comicSeriesRepositoryMock.findMatching(comicSeriesRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
