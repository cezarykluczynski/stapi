package com.cezarykluczynski.stapi.server.occupation.reader

import com.cezarykluczynski.stapi.client.v1.soap.OccupationBase
import com.cezarykluczynski.stapi.client.v1.soap.OccupationBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.OccupationBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.OccupationFull
import com.cezarykluczynski.stapi.client.v1.soap.OccupationFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.OccupationFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationBaseSoapMapper
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationFullSoapMapper
import com.cezarykluczynski.stapi.server.occupation.query.OccupationSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class OccupationSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private OccupationSoapQuery occupationSoapQueryBuilderMock

	private OccupationBaseSoapMapper occupationBaseSoapMapperMock

	private OccupationFullSoapMapper occupationFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private OccupationSoapReader occupationSoapReader

	void setup() {
		occupationSoapQueryBuilderMock = Mock()
		occupationBaseSoapMapperMock = Mock()
		occupationFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		occupationSoapReader = new OccupationSoapReader(occupationSoapQueryBuilderMock, occupationBaseSoapMapperMock, occupationFullSoapMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Occupation> occupationList = Lists.newArrayList()
		Page<Occupation> occupationPage = Mock()
		List<OccupationBase> soapOccupationList = Lists.newArrayList(new OccupationBase(uid: UID))
		OccupationBaseRequest occupationBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		OccupationBaseResponse occupationResponse = occupationSoapReader.readBase(occupationBaseRequest)

		then:
		1 * occupationSoapQueryBuilderMock.query(occupationBaseRequest) >> occupationPage
		1 * occupationPage.content >> occupationList
		1 * pageMapperMock.fromPageToSoapResponsePage(occupationPage) >> responsePage
		1 * occupationBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * occupationBaseSoapMapperMock.mapBase(occupationList) >> soapOccupationList
		0 * _
		occupationResponse.occupations[0].uid == UID
		occupationResponse.page == responsePage
		occupationResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		OccupationFull occupationFull = new OccupationFull(uid: UID)
		Occupation occupation = Mock()
		Page<Occupation> occupationPage = Mock()
		OccupationFullRequest occupationFullRequest = new OccupationFullRequest(uid: UID)

		when:
		OccupationFullResponse occupationFullResponse = occupationSoapReader.readFull(occupationFullRequest)

		then:
		1 * occupationSoapQueryBuilderMock.query(occupationFullRequest) >> occupationPage
		1 * occupationPage.content >> Lists.newArrayList(occupation)
		1 * occupationFullSoapMapperMock.mapFull(occupation) >> occupationFull
		0 * _
		occupationFullResponse.occupation.uid == UID
	}

	void "requires UID in full request"() {
		given:
		OccupationFullRequest occupationFullRequest = Mock()

		when:
		occupationSoapReader.readFull(occupationFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
