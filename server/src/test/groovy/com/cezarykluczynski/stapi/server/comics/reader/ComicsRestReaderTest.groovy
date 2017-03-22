package com.cezarykluczynski.stapi.server.comics.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsBase
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsFull
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.server.comics.dto.ComicsRestBeanParams
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseRestMapper
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsFullRestMapper
import com.cezarykluczynski.stapi.server.comics.query.ComicsRestQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ComicsRestReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private ComicsRestQuery comicsRestQueryBuilderMock

	private ComicsBaseRestMapper comicsBaseRestMapperMock

	private ComicsFullRestMapper comicsFullRestMapperMock

	private PageMapper pageMapperMock

	private ComicsRestReader comicsRestReader

	void setup() {
		comicsRestQueryBuilderMock = Mock(ComicsRestQuery)
		comicsBaseRestMapperMock = Mock(ComicsBaseRestMapper)
		comicsFullRestMapperMock = Mock(ComicsFullRestMapper)
		pageMapperMock = Mock(PageMapper)
		comicsRestReader = new ComicsRestReader(comicsRestQueryBuilderMock, comicsBaseRestMapperMock, comicsFullRestMapperMock,
				pageMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		ComicsRestBeanParams comicsRestBeanParams = Mock(ComicsRestBeanParams)
		List<ComicsBase> restComicsList = Lists.newArrayList(Mock(ComicsBase))
		List<Comics> comicsList = Lists.newArrayList(Mock(Comics))
		Page<Comics> comicsPage = Mock(Page)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		ComicsBaseResponse comicsResponseOutput = comicsRestReader.readBase(comicsRestBeanParams)

		then:
		1 * comicsRestQueryBuilderMock.query(comicsRestBeanParams) >> comicsPage
		1 * pageMapperMock.fromPageToRestResponsePage(comicsPage) >> responsePage
		1 * comicsPage.content >> comicsList
		1 * comicsBaseRestMapperMock.mapBase(comicsList) >> restComicsList
		0 * _
		comicsResponseOutput.comics == restComicsList
		comicsResponseOutput.page == responsePage
	}

	void "passed GUID to queryBuilder, then to mapper, and returns result"() {
		given:
		ComicsFull comicsFull = Mock(ComicsFull)
		Comics comics = Mock(Comics)
		List<Comics> comicsList = Lists.newArrayList(comics)
		Page<Comics> comicsPage = Mock(Page)

		when:
		ComicsFullResponse comicsResponseOutput = comicsRestReader.readFull(GUID)

		then:
		1 * comicsRestQueryBuilderMock.query(_ as ComicsRestBeanParams) >> { ComicsRestBeanParams comicsRestBeanParams ->
			assert comicsRestBeanParams.guid == GUID
			comicsPage
		}
		1 * comicsPage.content >> comicsList
		1 * comicsFullRestMapperMock.mapFull(comics) >> comicsFull
		0 * _
		comicsResponseOutput.comics == comicsFull
	}

}
