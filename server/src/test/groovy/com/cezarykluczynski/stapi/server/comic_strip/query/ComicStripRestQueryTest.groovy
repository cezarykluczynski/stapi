package com.cezarykluczynski.stapi.server.comic_strip.query

import com.cezarykluczynski.stapi.model.comic_strip.dto.ComicStripRequestDTO
import com.cezarykluczynski.stapi.model.comic_strip.repository.ComicStripRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.comic_strip.dto.ComicStripRestBeanParams
import com.cezarykluczynski.stapi.server.comic_strip.mapper.ComicStripBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class ComicStripRestQueryTest extends Specification {

	private ComicStripBaseRestMapper comicStripRestMapperMock

	private PageMapper pageMapperMock

	private ComicStripRepository comicStripRepositoryMock

	private ComicStripRestQuery comicStripRestQuery

	void setup() {
		comicStripRestMapperMock = Mock()
		pageMapperMock = Mock()
		comicStripRepositoryMock = Mock()
		comicStripRestQuery = new ComicStripRestQuery(comicStripRestMapperMock, pageMapperMock, comicStripRepositoryMock)
	}

	void "maps ComicStripRestBeanParams to ComicStripRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		ComicStripRestBeanParams comicStripRestBeanParams = Mock()
		ComicStripRequestDTO comicStripRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = comicStripRestQuery.query(comicStripRestBeanParams)

		then:
		1 * comicStripRestMapperMock.mapBase(comicStripRestBeanParams) >> comicStripRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(comicStripRestBeanParams) >> pageRequest
		1 * comicStripRepositoryMock.findMatching(comicStripRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
