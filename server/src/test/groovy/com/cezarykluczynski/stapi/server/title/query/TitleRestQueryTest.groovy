package com.cezarykluczynski.stapi.server.title.query

import com.cezarykluczynski.stapi.model.title.dto.TitleRequestDTO
import com.cezarykluczynski.stapi.model.title.repository.TitleRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.title.dto.TitleRestBeanParams
import com.cezarykluczynski.stapi.server.title.mapper.TitleBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class TitleRestQueryTest extends Specification {

	private TitleBaseRestMapper titleRestMapperMock

	private PageMapper pageMapperMock

	private TitleRepository titleRepositoryMock

	private TitleRestQuery titleRestQuery

	void setup() {
		titleRestMapperMock = Mock()
		pageMapperMock = Mock()
		titleRepositoryMock = Mock()
		titleRestQuery = new TitleRestQuery(titleRestMapperMock, pageMapperMock, titleRepositoryMock)
	}

	void "maps TitleRestBeanParams to TitleRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		TitleRestBeanParams titleRestBeanParams = Mock()
		TitleRequestDTO titleRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = titleRestQuery.query(titleRestBeanParams)

		then:
		1 * titleRestMapperMock.mapBase(titleRestBeanParams) >> titleRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(titleRestBeanParams) >> pageRequest
		1 * titleRepositoryMock.findMatching(titleRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
