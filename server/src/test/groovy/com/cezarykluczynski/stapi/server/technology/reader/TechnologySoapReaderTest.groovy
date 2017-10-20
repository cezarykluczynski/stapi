package com.cezarykluczynski.stapi.server.technology.reader

import com.cezarykluczynski.stapi.client.v1.soap.TechnologyBase
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFull
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.technology.entity.Technology
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyBaseSoapMapper
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyFullSoapMapper
import com.cezarykluczynski.stapi.server.technology.query.TechnologySoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class TechnologySoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private TechnologySoapQuery technologySoapQueryBuilderMock

	private TechnologyBaseSoapMapper technologyBaseSoapMapperMock

	private TechnologyFullSoapMapper technologyFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private TechnologySoapReader technologySoapReader

	void setup() {
		technologySoapQueryBuilderMock = Mock()
		technologyBaseSoapMapperMock = Mock()
		technologyFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		technologySoapReader = new TechnologySoapReader(technologySoapQueryBuilderMock, technologyBaseSoapMapperMock, technologyFullSoapMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Technology> technologyList = Lists.newArrayList()
		Page<Technology> technologyPage = Mock()
		List<TechnologyBase> soapTechnologyList = Lists.newArrayList(new TechnologyBase(uid: UID))
		TechnologyBaseRequest technologyBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		TechnologyBaseResponse technologyResponse = technologySoapReader.readBase(technologyBaseRequest)

		then:
		1 * technologySoapQueryBuilderMock.query(technologyBaseRequest) >> technologyPage
		1 * technologyPage.content >> technologyList
		1 * pageMapperMock.fromPageToSoapResponsePage(technologyPage) >> responsePage
		1 * technologyBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * technologyBaseSoapMapperMock.mapBase(technologyList) >> soapTechnologyList
		0 * _
		technologyResponse.technology[0].uid == UID
		technologyResponse.page == responsePage
		technologyResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		TechnologyFull technologyFull = new TechnologyFull(uid: UID)
		Technology technology = Mock()
		Page<Technology> technologyPage = Mock()
		TechnologyFullRequest technologyFullRequest = new TechnologyFullRequest(uid: UID)

		when:
		TechnologyFullResponse technologyFullResponse = technologySoapReader.readFull(technologyFullRequest)

		then:
		1 * technologySoapQueryBuilderMock.query(technologyFullRequest) >> technologyPage
		1 * technologyPage.content >> Lists.newArrayList(technology)
		1 * technologyFullSoapMapperMock.mapFull(technology) >> technologyFull
		0 * _
		technologyFullResponse.technology.uid == UID
	}

	void "requires UID in full request"() {
		given:
		TechnologyFullRequest technologyFullRequest = Mock()

		when:
		technologySoapReader.readFull(technologyFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
