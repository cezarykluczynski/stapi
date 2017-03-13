package com.cezarykluczynski.stapi.server.comics.reader

import com.cezarykluczynski.stapi.client.v1.soap.Comics as SOAPComics
import com.cezarykluczynski.stapi.client.v1.soap.ComicsRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicsResponse
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.model.comics.entity.Comics as DBComics
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsSoapMapper
import com.cezarykluczynski.stapi.server.comics.query.ComicsSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ComicsSoapReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private ComicsSoapQuery comicsSoapQueryBuilderMock

	private ComicsSoapMapper comicsSoapMapperMock

	private PageMapper pageMapperMock

	private ComicsSoapReader comicsSoapReader

	void setup() {
		comicsSoapQueryBuilderMock = Mock(ComicsSoapQuery)
		comicsSoapMapperMock = Mock(ComicsSoapMapper)
		pageMapperMock = Mock(PageMapper)
		comicsSoapReader = new ComicsSoapReader(comicsSoapQueryBuilderMock, comicsSoapMapperMock, pageMapperMock)
	}

	void "gets database entities and puts them into ComicsResponse"() {
		given:
		List<DBComics> dbComicsList = Lists.newArrayList()
		Page<DBComics> dbComicsPage = Mock(Page)
		dbComicsPage.content >> dbComicsList
		List<SOAPComics> soapComicsList = Lists.newArrayList(new SOAPComics(guid: GUID))
		ComicsRequest comicsRequest = Mock(ComicsRequest)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		ComicsResponse comicsResponse = comicsSoapReader.readBase(comicsRequest)

		then:
		1 * comicsSoapQueryBuilderMock.query(comicsRequest) >> dbComicsPage
		1 * pageMapperMock.fromPageToSoapResponsePage(dbComicsPage) >> responsePage
		1 * comicsSoapMapperMock.map(dbComicsList) >> soapComicsList
		comicsResponse.comics[0].guid == GUID
		comicsResponse.page == responsePage
	}

}
