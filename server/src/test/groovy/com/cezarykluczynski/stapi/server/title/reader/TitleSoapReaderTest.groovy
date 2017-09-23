package com.cezarykluczynski.stapi.server.title.reader

import com.cezarykluczynski.stapi.client.v1.soap.TitleBase
import com.cezarykluczynski.stapi.client.v1.soap.TitleBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TitleBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.TitleFull
import com.cezarykluczynski.stapi.client.v1.soap.TitleFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.TitleFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.title.entity.Title
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.title.mapper.TitleBaseSoapMapper
import com.cezarykluczynski.stapi.server.title.mapper.TitleFullSoapMapper
import com.cezarykluczynski.stapi.server.title.query.TitleSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class TitleSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private TitleSoapQuery titleSoapQueryBuilderMock

	private TitleBaseSoapMapper titleBaseSoapMapperMock

	private TitleFullSoapMapper titleFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private TitleSoapReader titleSoapReader

	void setup() {
		titleSoapQueryBuilderMock = Mock()
		titleBaseSoapMapperMock = Mock()
		titleFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		titleSoapReader = new TitleSoapReader(titleSoapQueryBuilderMock, titleBaseSoapMapperMock, titleFullSoapMapperMock, pageMapperMock,
				sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Title> titleList = Lists.newArrayList()
		Page<Title> titlePage = Mock()
		List<TitleBase> soapTitleList = Lists.newArrayList(new TitleBase(uid: UID))
		TitleBaseRequest titleBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		TitleBaseResponse titleResponse = titleSoapReader.readBase(titleBaseRequest)

		then:
		1 * titleSoapQueryBuilderMock.query(titleBaseRequest) >> titlePage
		1 * titlePage.content >> titleList
		1 * pageMapperMock.fromPageToSoapResponsePage(titlePage) >> responsePage
		1 * titleBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * titleBaseSoapMapperMock.mapBase(titleList) >> soapTitleList
		0 * _
		titleResponse.titles[0].uid == UID
		titleResponse.page == responsePage
		titleResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		TitleFull titleFull = new TitleFull(uid: UID)
		Title title = Mock()
		Page<Title> titlePage = Mock()
		TitleFullRequest titleFullRequest = new TitleFullRequest(uid: UID)

		when:
		TitleFullResponse titleFullResponse = titleSoapReader.readFull(titleFullRequest)

		then:
		1 * titleSoapQueryBuilderMock.query(titleFullRequest) >> titlePage
		1 * titlePage.content >> Lists.newArrayList(title)
		1 * titleFullSoapMapperMock.mapFull(title) >> titleFull
		0 * _
		titleFullResponse.title.uid == UID
	}

	void "requires UID in full request"() {
		given:
		TitleFullRequest titleFullRequest = Mock()

		when:
		titleSoapReader.readFull(titleFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
