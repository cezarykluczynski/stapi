package com.cezarykluczynski.stapi.server.comics.reader

import com.cezarykluczynski.stapi.client.v1.soap.ComicsBase
import com.cezarykluczynski.stapi.client.v1.soap.ComicsBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicsBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFull
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicsFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.comics.entity.Comics
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseSoapMapper
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsFullSoapMapper
import com.cezarykluczynski.stapi.server.comics.query.ComicsSoapQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ComicsSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private ComicsSoapQuery comicsSoapQueryBuilderMock

	private ComicsBaseSoapMapper comicsBaseSoapMapperMock

	private ComicsFullSoapMapper comicsFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private ComicsSoapReader comicsSoapReader

	void setup() {
		comicsSoapQueryBuilderMock = Mock()
		comicsBaseSoapMapperMock = Mock()
		comicsFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		comicsSoapReader = new ComicsSoapReader(comicsSoapQueryBuilderMock, comicsBaseSoapMapperMock, comicsFullSoapMapperMock, pageMapperMock,
				sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Comics> comicsList = Lists.newArrayList()
		Page<Comics> comicsPage = Mock()
		List<ComicsBase> soapComicsList = Lists.newArrayList(new ComicsBase(uid: UID))
		ComicsBaseRequest comicsBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		ComicsBaseResponse comicsResponse = comicsSoapReader.readBase(comicsBaseRequest)

		then:
		1 * comicsSoapQueryBuilderMock.query(comicsBaseRequest) >> comicsPage
		1 * comicsPage.content >> comicsList
		1 * pageMapperMock.fromPageToSoapResponsePage(comicsPage) >> responsePage
		1 * comicsBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * comicsBaseSoapMapperMock.mapBase(comicsList) >> soapComicsList
		0 * _
		comicsResponse.comics[0].uid == UID
		comicsResponse.page == responsePage
		comicsResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		ComicsFull comicsFull = new ComicsFull(uid: UID)
		Comics comics = Mock()
		Page<Comics> comicsPage = Mock()
		ComicsFullRequest comicsFullRequest = new ComicsFullRequest(uid: UID)

		when:
		ComicsFullResponse comicsFullResponse = comicsSoapReader.readFull(comicsFullRequest)

		then:
		1 * comicsSoapQueryBuilderMock.query(comicsFullRequest) >> comicsPage
		1 * comicsPage.content >> Lists.newArrayList(comics)
		1 * comicsFullSoapMapperMock.mapFull(comics) >> comicsFull
		0 * _
		comicsFullResponse.comics.uid == UID
	}

	void "requires UID in full request"() {
		given:
		ComicsFullRequest comicsFullRequest = Mock()

		when:
		comicsSoapReader.readFull(comicsFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
