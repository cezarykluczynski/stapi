package com.cezarykluczynski.stapi.server.comics.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.Comics as RESTComics
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.comics.dto.ComicsRestBeanParams
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsRestMapper
import com.cezarykluczynski.stapi.server.comics.query.ComicsRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ComicsRestReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private ComicsRestQuery comicsRestQueryBuilderMock

	private ComicsRestMapper comicsRestMapperMock

	private PageMapper pageMapperMock

	private ComicsRestReader comicsRestReader

	void setup() {
		comicsRestQueryBuilderMock = Mock(ComicsRestQuery)
		comicsRestMapperMock = Mock(ComicsRestMapper)
		pageMapperMock = Mock(PageMapper)
		comicsRestReader = new ComicsRestReader(comicsRestQueryBuilderMock, comicsRestMapperMock, pageMapperMock)
	}

	void "gets database entities and puts them into ComicsResponse"() {
		given:
		List<Comics> dbComicsList = Lists.newArrayList()
		Page<Comics> dbComicsPage = Mock(Page)
		dbComicsPage.content >> dbComicsList
		List<RESTComics> soapComicsList = Lists.newArrayList(new RESTComics(guid: GUID))
		ComicsRestBeanParams seriesRestBeanParams = Mock(ComicsRestBeanParams)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		ComicsResponse comicsResponse = comicsRestReader.read(seriesRestBeanParams)

		then:
		1 * comicsRestQueryBuilderMock.query(seriesRestBeanParams) >> dbComicsPage
		1 * pageMapperMock.fromPageToRestResponsePage(dbComicsPage) >> responsePage
		1 * comicsRestMapperMock.map(dbComicsList) >> soapComicsList
		comicsResponse.comics[0].guid == GUID
		comicsResponse.page == responsePage
	}

}
