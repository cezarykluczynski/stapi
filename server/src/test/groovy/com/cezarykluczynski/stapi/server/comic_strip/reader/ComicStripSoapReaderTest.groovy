package com.cezarykluczynski.stapi.server.comic_strip.reader

import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBase
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFull
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ComicStripFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip
import com.cezarykluczynski.stapi.server.comic_strip.mapper.ComicStripBaseSoapMapper
import com.cezarykluczynski.stapi.server.comic_strip.mapper.ComicStripFullSoapMapper
import com.cezarykluczynski.stapi.server.comic_strip.query.ComicStripSoapQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ComicStripSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private ComicStripSoapQuery comicStripSoapQueryBuilderMock

	private ComicStripBaseSoapMapper comicStripBaseSoapMapperMock

	private ComicStripFullSoapMapper comicStripFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private ComicStripSoapReader comicStripSoapReader

	void setup() {
		comicStripSoapQueryBuilderMock = Mock()
		comicStripBaseSoapMapperMock = Mock()
		comicStripFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		comicStripSoapReader = new ComicStripSoapReader(comicStripSoapQueryBuilderMock, comicStripBaseSoapMapperMock, comicStripFullSoapMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<ComicStrip> comicStripList = Lists.newArrayList()
		Page<ComicStrip> comicStripPage = Mock()
		List<ComicStripBase> soapComicStripList = Lists.newArrayList(new ComicStripBase(uid: UID))
		ComicStripBaseRequest comicStripBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		ComicStripBaseResponse comicStripResponse = comicStripSoapReader.readBase(comicStripBaseRequest)

		then:
		1 * comicStripSoapQueryBuilderMock.query(comicStripBaseRequest) >> comicStripPage
		1 * comicStripPage.content >> comicStripList
		1 * pageMapperMock.fromPageToSoapResponsePage(comicStripPage) >> responsePage
		1 * comicStripBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * comicStripBaseSoapMapperMock.mapBase(comicStripList) >> soapComicStripList
		0 * _
		comicStripResponse.comicStrips[0].uid == UID
		comicStripResponse.page == responsePage
		comicStripResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		ComicStripFull comicStripFull = new ComicStripFull(uid: UID)
		ComicStrip comicStrip = Mock()
		Page<ComicStrip> comicStripPage = Mock()
		ComicStripFullRequest comicStripFullRequest = new ComicStripFullRequest(uid: UID)

		when:
		ComicStripFullResponse comicStripFullResponse = comicStripSoapReader.readFull(comicStripFullRequest)

		then:
		1 * comicStripSoapQueryBuilderMock.query(comicStripFullRequest) >> comicStripPage
		1 * comicStripPage.content >> Lists.newArrayList(comicStrip)
		1 * comicStripFullSoapMapperMock.mapFull(comicStrip) >> comicStripFull
		0 * _
		comicStripFullResponse.comicStrip.uid == UID
	}

	void "requires UID in full request"() {
		given:
		ComicStripFullRequest comicStripFullRequest = Mock()

		when:
		comicStripSoapReader.readFull(comicStripFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
