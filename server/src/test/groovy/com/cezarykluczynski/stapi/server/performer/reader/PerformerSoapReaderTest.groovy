package com.cezarykluczynski.stapi.server.performer.reader

import com.cezarykluczynski.stapi.client.v1.soap.PerformerBase
import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFull
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerSoapMapper
import com.cezarykluczynski.stapi.server.performer.query.PerformerSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class PerformerSoapReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private PerformerSoapQuery performerSoapQueryBuilderMock

	private PerformerSoapMapper performerSoapMapperMock

	private PageMapper pageMapperMock

	private PerformerSoapReader performerSoapReader

	void setup() {
		performerSoapQueryBuilderMock = Mock(PerformerSoapQuery)
		performerSoapMapperMock = Mock(PerformerSoapMapper)
		pageMapperMock = Mock(PageMapper)
		performerSoapReader = new PerformerSoapReader(performerSoapQueryBuilderMock, performerSoapMapperMock, pageMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Performer> performerList = Lists.newArrayList()
		Page<Performer> performerPage = Mock(Page)
		List<PerformerBase> soapPerformerList = Lists.newArrayList(new PerformerBase(guid: GUID))
		PerformerBaseRequest performerBaseRequest = Mock(PerformerBaseRequest)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		PerformerBaseResponse performerResponse = performerSoapReader.readBase(performerBaseRequest)

		then:
		1 * performerSoapQueryBuilderMock.query(performerBaseRequest) >> performerPage
		1 * performerPage.content >> performerList
		1 * pageMapperMock.fromPageToSoapResponsePage(performerPage) >> responsePage
		1 * performerSoapMapperMock.mapBase(performerList) >> soapPerformerList
		performerResponse.performers[0].guid == GUID
		performerResponse.page == responsePage
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		PerformerFull performerFull = new PerformerFull(guid: GUID)
		Performer performer = Mock(Performer)
		Page<Performer> performerPage = Mock(Page)
		PerformerFullRequest performerFullRequest = Mock(PerformerFullRequest)

		when:
		PerformerFullResponse performerFullResponse = performerSoapReader.readFull(performerFullRequest)

		then:
		1 * performerSoapQueryBuilderMock.query(performerFullRequest) >> performerPage
		1 * performerPage.content >> Lists.newArrayList(performer)
		1 * performerSoapMapperMock.mapFull(performer) >> performerFull
		performerFullResponse.performer.guid == GUID
	}

}
