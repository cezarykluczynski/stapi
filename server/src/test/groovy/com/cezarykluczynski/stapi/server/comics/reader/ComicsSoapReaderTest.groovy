package com.cezarykluczynski.stapi.server.comics.reader

import com.cezarykluczynski.stapi.client.v1.soap.ComicsBase
import com.cezarykluczynski.stapi.client.v1.soap.ComicsBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicsBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFull
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseSoapMapper
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsFullSoapMapper
import com.cezarykluczynski.stapi.server.comics.query.ComicsSoapQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ComicsSoapReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private ComicsSoapQuery comicsSoapQueryBuilderMock

	private ComicsBaseSoapMapper comicsBaseSoapMapperMock

	private ComicsFullSoapMapper comicsFullSoapMapperMock

	private PageMapper pageMapperMock

	private ComicsSoapReader comicsSoapReader

	void setup() {
		comicsSoapQueryBuilderMock = Mock(ComicsSoapQuery)
		comicsBaseSoapMapperMock = Mock(ComicsBaseSoapMapper)
		comicsFullSoapMapperMock = Mock(ComicsFullSoapMapper)
		pageMapperMock = Mock(PageMapper)
		comicsSoapReader = new ComicsSoapReader(comicsSoapQueryBuilderMock, comicsBaseSoapMapperMock, comicsFullSoapMapperMock,
				pageMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Comics> comicsList = Lists.newArrayList()
		Page<Comics> comicsPage = Mock(Page)
		List<ComicsBase> soapComicsList = Lists.newArrayList(new ComicsBase(guid: GUID))
		ComicsBaseRequest comicsBaseRequest = Mock(ComicsBaseRequest)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		ComicsBaseResponse comicsResponse = comicsSoapReader.readBase(comicsBaseRequest)

		then:
		1 * comicsSoapQueryBuilderMock.query(comicsBaseRequest) >> comicsPage
		1 * comicsPage.content >> comicsList
		1 * pageMapperMock.fromPageToSoapResponsePage(comicsPage) >> responsePage
		1 * comicsBaseSoapMapperMock.mapBase(comicsList) >> soapComicsList
		comicsResponse.comics[0].guid == GUID
		comicsResponse.page == responsePage
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		ComicsFull comicsFull = new ComicsFull(guid: GUID)
		Comics comics = Mock(Comics)
		Page<Comics> comicsPage = Mock(Page)
		ComicsFullRequest comicsFullRequest = Mock(ComicsFullRequest)

		when:
		ComicsFullResponse comicsFullResponse = comicsSoapReader.readFull(comicsFullRequest)

		then:
		1 * comicsSoapQueryBuilderMock.query(comicsFullRequest) >> comicsPage
		1 * comicsPage.content >> Lists.newArrayList(comics)
		1 * comicsFullSoapMapperMock.mapFull(comics) >> comicsFull
		comicsFullResponse.comics.guid == GUID
	}

}
