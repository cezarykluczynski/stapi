package com.cezarykluczynski.stapi.server.astronomicalObject.reader

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObject as SOAPAstronomicalObject
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectRequest
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectResponse
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject as DBAstronomicalObject
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectSoapMapper
import com.cezarykluczynski.stapi.server.astronomicalObject.query.AstronomicalObjectSoapQuery
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

	void "gets database entities and puts them into AstronomicalObjectResponse"() {
		given:
		List<DBAstronomicalObject> dbAstronomicalObjectList = Lists.newArrayList()
		Page<DBAstronomicalObject> dbAstronomicalObjectPage = Mock(Page)
		dbAstronomicalObjectPage.content >> dbAstronomicalObjectList
		List<SOAPAstronomicalObject> soapAstronomicalObjectList = Lists.newArrayList(new SOAPAstronomicalObject(guid: GUID))
		AstronomicalObjectRequest astronomicalObjectRequest = Mock(AstronomicalObjectRequest)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		AstronomicalObjectResponse astronomicalObjectResponse = astronomicalObjectSoapReader.read(astronomicalObjectRequest)

		then:
		1 * astronomicalObjectSoapQueryBuilderMock.query(astronomicalObjectRequest) >> dbAstronomicalObjectPage
		1 * pageMapperMock.fromPageToSoapResponsePage(dbAstronomicalObjectPage) >> responsePage
		1 * astronomicalObjectSoapMapperMock.map(dbAstronomicalObjectList) >> soapAstronomicalObjectList
		astronomicalObjectResponse.astronomicalObjects[0].guid == GUID
		astronomicalObjectResponse.page == responsePage
	}

}
