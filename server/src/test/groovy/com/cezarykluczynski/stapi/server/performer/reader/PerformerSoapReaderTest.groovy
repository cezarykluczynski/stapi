package com.cezarykluczynski.stapi.server.performer.reader

import com.cezarykluczynski.stapi.client.v1.soap.Performer as SOAPPerformer
import com.cezarykluczynski.stapi.client.v1.soap.PerformerRequest
import com.cezarykluczynski.stapi.client.v1.soap.PerformerResponse
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.model.performer.entity.Performer as DBPerformer
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerSoapMapper
import com.cezarykluczynski.stapi.server.performer.query.PerformerQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class PerformerSoapReaderTest extends Specification {

	private static final Long ID = 1L

	private PerformerQuery performerQueryBuilderMock

	private PerformerSoapMapper performerSoapMapperMock

	private PageMapper pageMapperMock

	private PerformerSoapReader performerSoapReader

	def setup() {
		performerQueryBuilderMock = Mock(PerformerQuery)
		performerSoapMapperMock = Mock(PerformerSoapMapper)
		pageMapperMock = Mock(PageMapper)
		performerSoapReader = new PerformerSoapReader(performerQueryBuilderMock, performerSoapMapperMock, pageMapperMock)
	}

	def "gets database entities and puts them into PerformerResponse"() {
		given:
		List<DBPerformer> dbPerformerList = Lists.newArrayList()
		Page<DBPerformer> dbPerformerPage = Mock(Page) {
			getContent() >> dbPerformerList
		}
		List<SOAPPerformer> soapPerformerList = Lists.newArrayList(new SOAPPerformer(id: ID))
		PerformerRequest performerRequest = Mock(PerformerRequest)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		PerformerResponse performerResponse = performerSoapReader.read(performerRequest)

		then:
		1 * performerQueryBuilderMock.query(performerRequest) >> dbPerformerPage
		1 * pageMapperMock.fromPageToSoapResponsePage(dbPerformerPage) >> responsePage
		1 * performerSoapMapperMock.map(dbPerformerList) >> soapPerformerList
		performerResponse.performers[0].id == ID
		performerResponse.page == responsePage
	}

}
