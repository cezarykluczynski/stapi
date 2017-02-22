package com.cezarykluczynski.stapi.server.comicStrip.query

import com.cezarykluczynski.stapi.model.comicStrip.dto.ComicStripRequestDTO
import com.cezarykluczynski.stapi.model.comicStrip.repository.ComicStripRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.comicStrip.dto.ComicStripRestBeanParams
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class ComicStripRestQueryTest extends Specification {

	private ComicStripRestMapper comicStripRestMapperMock

	private PageMapper pageMapperMock

	private ComicStripRepository comicStripRepositoryMock

	private ComicStripRestQuery comicStripRestQuery

	void setup() {
		comicStripRestMapperMock = Mock(ComicStripRestMapper)
		pageMapperMock = Mock(PageMapper)
		comicStripRepositoryMock = Mock(ComicStripRepository)
		comicStripRestQuery = new ComicStripRestQuery(comicStripRestMapperMock, pageMapperMock, comicStripRepositoryMock)
	}

	void "maps ComicStripRestBeanParams to ComicStripRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock(PageRequest)
		ComicStripRestBeanParams comicStripRestBeanParams = Mock(ComicStripRestBeanParams) {

		}
		ComicStripRequestDTO comicStripRequestDTO = Mock(ComicStripRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = comicStripRestQuery.query(comicStripRestBeanParams)

		then:
		1 * comicStripRestMapperMock.map(comicStripRestBeanParams) >> comicStripRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(comicStripRestBeanParams) >> pageRequest
		1 * comicStripRepositoryMock.findMatching(comicStripRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
