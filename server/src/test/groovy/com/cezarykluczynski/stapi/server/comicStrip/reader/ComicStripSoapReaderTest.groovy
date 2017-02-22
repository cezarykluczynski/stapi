package com.cezarykluczynski.stapi.server.comicStrip.reader

import com.cezarykluczynski.stapi.client.v1.soap.ComicStrip as SOAPComicStrip
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripResponse
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip as DBComicStrip
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripSoapMapper
import com.cezarykluczynski.stapi.server.comicStrip.query.ComicStripSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ComicStripSoapReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private ComicStripSoapQuery comicStripSoapQueryBuilderMock

	private ComicStripSoapMapper comicStripSoapMapperMock

	private PageMapper pageMapperMock

	private ComicStripSoapReader comicStripSoapReader

	void setup() {
		comicStripSoapQueryBuilderMock = Mock(ComicStripSoapQuery)
		comicStripSoapMapperMock = Mock(ComicStripSoapMapper)
		pageMapperMock = Mock(PageMapper)
		comicStripSoapReader = new ComicStripSoapReader(comicStripSoapQueryBuilderMock, comicStripSoapMapperMock, pageMapperMock)
	}

	void "gets database entities and puts them into ComicStripResponse"() {
		given:
		List<DBComicStrip> dbComicStripList = Lists.newArrayList()
		Page<DBComicStrip> dbComicStripPage = Mock(Page)
		dbComicStripPage.content >> dbComicStripList
		List<SOAPComicStrip> soapComicStripList = Lists.newArrayList(new SOAPComicStrip(guid: GUID))
		ComicStripRequest comicStripRequest = Mock(ComicStripRequest)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		ComicStripResponse comicStripResponse = comicStripSoapReader.read(comicStripRequest)

		then:
		1 * comicStripSoapQueryBuilderMock.query(comicStripRequest) >> dbComicStripPage
		1 * pageMapperMock.fromPageToSoapResponsePage(dbComicStripPage) >> responsePage
		1 * comicStripSoapMapperMock.map(dbComicStripList) >> soapComicStripList
		comicStripResponse.comicStrip[0].guid == GUID
		comicStripResponse.page == responsePage
	}

}
