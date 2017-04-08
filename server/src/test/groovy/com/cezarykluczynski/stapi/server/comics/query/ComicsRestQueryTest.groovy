package com.cezarykluczynski.stapi.server.comics.query

import com.cezarykluczynski.stapi.model.comics.dto.ComicsRequestDTO
import com.cezarykluczynski.stapi.model.comics.repository.ComicsRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.comics.dto.ComicsRestBeanParams
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class ComicsRestQueryTest extends Specification {

	private ComicsBaseRestMapper comicsBaseRestMapperMock

	private PageMapper pageMapperMock

	private ComicsRepository comicsRepositoryMock

	private ComicsRestQuery comicsRestQuery

	void setup() {
		comicsBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		comicsRepositoryMock = Mock()
		comicsRestQuery = new ComicsRestQuery(comicsBaseRestMapperMock, pageMapperMock, comicsRepositoryMock)
	}

	void "maps ComicsRestBeanParams to ComicsRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		ComicsRestBeanParams comicsRestBeanParams = Mock()
		ComicsRequestDTO comicsRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = comicsRestQuery.query(comicsRestBeanParams)

		then:
		1 * comicsBaseRestMapperMock.mapBase(comicsRestBeanParams) >> comicsRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(comicsRestBeanParams) >> pageRequest
		1 * comicsRepositoryMock.findMatching(comicsRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
