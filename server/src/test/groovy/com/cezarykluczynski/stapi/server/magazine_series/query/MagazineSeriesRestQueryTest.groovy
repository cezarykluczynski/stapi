package com.cezarykluczynski.stapi.server.magazine_series.query

import com.cezarykluczynski.stapi.model.magazine_series.dto.MagazineSeriesRequestDTO
import com.cezarykluczynski.stapi.model.magazine_series.repository.MagazineSeriesRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.magazine_series.dto.MagazineSeriesRestBeanParams
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class MagazineSeriesRestQueryTest extends Specification {

	private MagazineSeriesBaseRestMapper magazineSeriesRestMapperMock

	private PageMapper pageMapperMock

	private MagazineSeriesRepository magazineSeriesRepositoryMock

	private MagazineSeriesRestQuery magazineSeriesRestQuery

	void setup() {
		magazineSeriesRestMapperMock = Mock()
		pageMapperMock = Mock()
		magazineSeriesRepositoryMock = Mock()
		magazineSeriesRestQuery = new MagazineSeriesRestQuery(magazineSeriesRestMapperMock, pageMapperMock, magazineSeriesRepositoryMock)
	}

	void "maps MagazineSeriesRestBeanParams to MagazineSeriesRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		MagazineSeriesRestBeanParams magazineSeriesRestBeanParams = Mock()
		MagazineSeriesRequestDTO magazineSeriesRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = magazineSeriesRestQuery.query(magazineSeriesRestBeanParams)

		then:
		1 * magazineSeriesRestMapperMock.mapBase(magazineSeriesRestBeanParams) >> magazineSeriesRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(magazineSeriesRestBeanParams) >> pageRequest
		1 * magazineSeriesRepositoryMock.findMatching(magazineSeriesRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
