package com.cezarykluczynski.stapi.server.astronomical_object.reader

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBase
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFull
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectBaseSoapMapper
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectFullSoapMapper
import com.cezarykluczynski.stapi.server.astronomical_object.query.AstronomicalObjectSoapQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class AstronomicalObjectSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private AstronomicalObjectSoapQuery astronomicalObjectSoapQueryBuilderMock

	private AstronomicalObjectBaseSoapMapper astronomicalObjectBaseSoapMapperMock

	private AstronomicalObjectFullSoapMapper astronomicalObjectFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private AstronomicalObjectSoapReader astronomicalObjectSoapReader

	void setup() {
		astronomicalObjectSoapQueryBuilderMock = Mock()
		astronomicalObjectBaseSoapMapperMock = Mock()
		astronomicalObjectFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		astronomicalObjectSoapReader = new AstronomicalObjectSoapReader(astronomicalObjectSoapQueryBuilderMock, astronomicalObjectBaseSoapMapperMock,
				astronomicalObjectFullSoapMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<AstronomicalObject> astronomicalObjectList = Lists.newArrayList()
		Page<AstronomicalObject> astronomicalObjectPage = Mock()
		List<AstronomicalObjectBase> soapAstronomicalObjectList = Lists.newArrayList(new AstronomicalObjectBase(uid: UID))
		AstronomicalObjectBaseRequest astronomicalObjectBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		AstronomicalObjectBaseResponse astronomicalObjectResponse = astronomicalObjectSoapReader.readBase(astronomicalObjectBaseRequest)

		then:
		1 * astronomicalObjectSoapQueryBuilderMock.query(astronomicalObjectBaseRequest) >> astronomicalObjectPage
		1 * astronomicalObjectPage.content >> astronomicalObjectList
		1 * pageMapperMock.fromPageToSoapResponsePage(astronomicalObjectPage) >> responsePage
		1 * astronomicalObjectBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * astronomicalObjectBaseSoapMapperMock.mapBase(astronomicalObjectList) >> soapAstronomicalObjectList
		0 * _
		astronomicalObjectResponse.astronomicalObjects[0].uid == UID
		astronomicalObjectResponse.page == responsePage
		astronomicalObjectResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		AstronomicalObjectFull astronomicalObjectFull = new AstronomicalObjectFull(uid: UID)
		AstronomicalObject astronomicalObject = Mock()
		Page<AstronomicalObject> astronomicalObjectPage = Mock()
		AstronomicalObjectFullRequest astronomicalObjectFullRequest = new AstronomicalObjectFullRequest(uid: UID)

		when:
		AstronomicalObjectFullResponse astronomicalObjectFullResponse = astronomicalObjectSoapReader.readFull(astronomicalObjectFullRequest)

		then:
		1 * astronomicalObjectSoapQueryBuilderMock.query(astronomicalObjectFullRequest) >> astronomicalObjectPage
		1 * astronomicalObjectPage.content >> Lists.newArrayList(astronomicalObject)
		1 * astronomicalObjectFullSoapMapperMock.mapFull(astronomicalObject) >> astronomicalObjectFull
		0 * _
		astronomicalObjectFullResponse.astronomicalObject.uid == UID
	}

	void "requires UID in full request"() {
		given:
		AstronomicalObjectFullRequest astronomicalObjectFullRequest = Mock()

		when:
		astronomicalObjectSoapReader.readFull(astronomicalObjectFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
