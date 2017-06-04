package com.cezarykluczynski.stapi.server.performer.reader

import com.cezarykluczynski.stapi.client.v1.soap.PerformerBase
import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFull
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseSoapMapper
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerFullSoapMapper
import com.cezarykluczynski.stapi.server.performer.query.PerformerSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class PerformerSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private PerformerSoapQuery performerSoapQueryBuilderMock

	private PerformerBaseSoapMapper performerBaseSoapMapperMock

	private PerformerFullSoapMapper performerFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private PerformerSoapReader performerSoapReader

	void setup() {
		performerSoapQueryBuilderMock = Mock()
		performerBaseSoapMapperMock = Mock()
		performerFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		performerSoapReader = new PerformerSoapReader(performerSoapQueryBuilderMock, performerBaseSoapMapperMock, performerFullSoapMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Performer> performerList = Lists.newArrayList()
		Page<Performer> performerPage = Mock()
		List<PerformerBase> soapPerformerList = Lists.newArrayList(new PerformerBase(uid: UID))
		PerformerBaseRequest performerBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		PerformerBaseResponse performerResponse = performerSoapReader.readBase(performerBaseRequest)

		then:
		1 * performerSoapQueryBuilderMock.query(performerBaseRequest) >> performerPage
		1 * performerPage.content >> performerList
		1 * pageMapperMock.fromPageToSoapResponsePage(performerPage) >> responsePage
		1 * performerBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * performerBaseSoapMapperMock.mapBase(performerList) >> soapPerformerList
		0 * _
		performerResponse.performers[0].uid == UID
		performerResponse.page == responsePage
		performerResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		PerformerFull performerFull = new PerformerFull(uid: UID)
		Performer performer = Mock()
		Page<Performer> performerPage = Mock()
		PerformerFullRequest performerFullRequest = new PerformerFullRequest(uid: UID)

		when:
		PerformerFullResponse performerFullResponse = performerSoapReader.readFull(performerFullRequest)

		then:
		1 * performerSoapQueryBuilderMock.query(performerFullRequest) >> performerPage
		1 * performerPage.content >> Lists.newArrayList(performer)
		1 * performerFullSoapMapperMock.mapFull(performer) >> performerFull
		0 * _
		performerFullResponse.performer.uid == UID
	}

	void "requires UID in full request"() {
		given:
		PerformerFullRequest performerFullRequest = Mock()

		when:
		performerSoapReader.readFull(performerFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
