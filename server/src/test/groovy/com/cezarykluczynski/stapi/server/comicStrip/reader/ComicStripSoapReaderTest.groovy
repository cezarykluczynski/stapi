package com.cezarykluczynski.stapi.server.comicStrip.reader

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBase
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFull
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripBaseSoapMapper
import com.cezarykluczynski.stapi.server.comicStrip.mapper.ComicStripFullSoapMapper
import com.cezarykluczynski.stapi.server.comicStrip.query.ComicStripSoapQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ComicStripSoapReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private ComicStripSoapQuery comicStripSoapQueryBuilderMock

	private ComicStripBaseSoapMapper comicStripBaseSoapMapperMock

	private ComicStripFullSoapMapper comicStripFullSoapMapperMock

	private PageMapper pageMapperMock

	private ComicStripSoapReader comicStripSoapReader

	void setup() {
		comicStripSoapQueryBuilderMock = Mock()
		comicStripBaseSoapMapperMock = Mock()
		comicStripFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		comicStripSoapReader = new ComicStripSoapReader(comicStripSoapQueryBuilderMock, comicStripBaseSoapMapperMock, comicStripFullSoapMapperMock,
				pageMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<ComicStrip> comicStripList = Lists.newArrayList()
		Page<ComicStrip> comicStripPage = Mock()
		List<ComicStripBase> soapComicStripList = Lists.newArrayList(new ComicStripBase(guid: GUID))
		ComicStripBaseRequest comicStripBaseRequest = Mock()
		ResponsePage responsePage = Mock()

		when:
		ComicStripBaseResponse comicStripResponse = comicStripSoapReader.readBase(comicStripBaseRequest)

		then:
		1 * comicStripSoapQueryBuilderMock.query(comicStripBaseRequest) >> comicStripPage
		1 * comicStripPage.content >> comicStripList
		1 * pageMapperMock.fromPageToSoapResponsePage(comicStripPage) >> responsePage
		1 * comicStripBaseSoapMapperMock.mapBase(comicStripList) >> soapComicStripList
		comicStripResponse.comicStrips[0].guid == GUID
		comicStripResponse.page == responsePage
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		ComicStripFull comicStripFull = new ComicStripFull(guid: GUID)
		ComicStrip comicStrip = Mock()
		Page<ComicStrip> comicStripPage = Mock()
		ComicStripFullRequest comicStripFullRequest = Mock()

		when:
		ComicStripFullResponse comicStripFullResponse = comicStripSoapReader.readFull(comicStripFullRequest)

		then:
		1 * comicStripSoapQueryBuilderMock.query(comicStripFullRequest) >> comicStripPage
		1 * comicStripPage.content >> Lists.newArrayList(comicStrip)
		1 * comicStripFullSoapMapperMock.mapFull(comicStrip) >> comicStripFull
		comicStripFullResponse.comicStrip.guid == GUID
	}

}
