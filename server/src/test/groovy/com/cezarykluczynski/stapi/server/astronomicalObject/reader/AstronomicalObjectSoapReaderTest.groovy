package com.cezarykluczynski.stapi.server.astronomicalObject.reader

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBase
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFull
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectSoapMapper
import com.cezarykluczynski.stapi.server.astronomicalObject.query.AstronomicalObjectSoapQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class AstronomicalObjectSoapReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private AstronomicalObjectSoapQuery astronomicalObjectSoapQueryBuilderMock

	private AstronomicalObjectSoapMapper astronomicalObjectSoapMapperMock

	private PageMapper pageMapperMock

	private AstronomicalObjectSoapReader astronomicalObjectSoapReader

	void setup() {
		astronomicalObjectSoapQueryBuilderMock = Mock(AstronomicalObjectSoapQuery)
		astronomicalObjectSoapMapperMock = Mock(AstronomicalObjectSoapMapper)
		pageMapperMock = Mock(PageMapper)
		astronomicalObjectSoapReader = new AstronomicalObjectSoapReader(astronomicalObjectSoapQueryBuilderMock, astronomicalObjectSoapMapperMock,
				pageMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<AstronomicalObject> astronomicalObjectList = Lists.newArrayList()
		Page<AstronomicalObject> astronomicalObjectPage = Mock(Page)
		List<AstronomicalObjectBase> soapAstronomicalObjectList = Lists.newArrayList(new AstronomicalObjectBase(guid: GUID))
		AstronomicalObjectBaseRequest astronomicalObjectBaseRequest = Mock(AstronomicalObjectBaseRequest)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		AstronomicalObjectBaseResponse astronomicalObjectResponse = astronomicalObjectSoapReader.readBase(astronomicalObjectBaseRequest)

		then:
		1 * astronomicalObjectSoapQueryBuilderMock.query(astronomicalObjectBaseRequest) >> astronomicalObjectPage
		1 * astronomicalObjectPage.content >> astronomicalObjectList
		1 * pageMapperMock.fromPageToSoapResponsePage(astronomicalObjectPage) >> responsePage
		1 * astronomicalObjectSoapMapperMock.mapBase(astronomicalObjectList) >> soapAstronomicalObjectList
		astronomicalObjectResponse.astronomicalObjects[0].guid == GUID
		astronomicalObjectResponse.page == responsePage
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		AstronomicalObjectFull astronomicalObjectFull = new AstronomicalObjectFull(guid: GUID)
		AstronomicalObject astronomicalObject = Mock(AstronomicalObject)
		Page<AstronomicalObject> astronomicalObjectPage = Mock(Page)
		AstronomicalObjectFullRequest astronomicalObjectFullRequest = Mock(AstronomicalObjectFullRequest)

		when:
		AstronomicalObjectFullResponse astronomicalObjectFullResponse = astronomicalObjectSoapReader.readFull(astronomicalObjectFullRequest)

		then:
		1 * astronomicalObjectSoapQueryBuilderMock.query(astronomicalObjectFullRequest) >> astronomicalObjectPage
		1 * astronomicalObjectPage.content >> Lists.newArrayList(astronomicalObject)
		1 * astronomicalObjectSoapMapperMock.mapFull(astronomicalObject) >> astronomicalObjectFull
		astronomicalObjectFullResponse.astronomicalObject.guid == GUID
	}

}
