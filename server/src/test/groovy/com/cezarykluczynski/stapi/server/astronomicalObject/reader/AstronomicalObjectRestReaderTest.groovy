package com.cezarykluczynski.stapi.server.astronomicalObject.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObject as RESTAstronomicalObject
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.astronomicalObject.dto.AstronomicalObjectRestBeanParams
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectRestMapper
import com.cezarykluczynski.stapi.server.astronomicalObject.query.AstronomicalObjectRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class AstronomicalObjectRestReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private AstronomicalObjectRestQuery astronomicalObjectRestQueryBuilderMock

	private AstronomicalObjectRestMapper astronomicalObjectRestMapperMock

	private PageMapper pageMapperMock

	private AstronomicalObjectRestReader astronomicalObjectRestReader

	void setup() {
		astronomicalObjectRestQueryBuilderMock = Mock(AstronomicalObjectRestQuery)
		astronomicalObjectRestMapperMock = Mock(AstronomicalObjectRestMapper)
		pageMapperMock = Mock(PageMapper)
		astronomicalObjectRestReader = new AstronomicalObjectRestReader(astronomicalObjectRestQueryBuilderMock, astronomicalObjectRestMapperMock,
				pageMapperMock)
	}

	void "gets database entities and puts them into AstronomicalObjectResponse"() {
		given:
		List<AstronomicalObject> dbAstronomicalObjectList = Lists.newArrayList()
		Page<AstronomicalObject> dbAstronomicalObjectPage = Mock(Page)
		dbAstronomicalObjectPage.content >> dbAstronomicalObjectList
		List<RESTAstronomicalObject> soapAstronomicalObjectList = Lists.newArrayList(new RESTAstronomicalObject(guid: GUID))
		AstronomicalObjectRestBeanParams seriesRestBeanParams = Mock(AstronomicalObjectRestBeanParams)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		AstronomicalObjectResponse astronomicalObjectResponse = astronomicalObjectRestReader.readBase(seriesRestBeanParams)

		then:
		1 * astronomicalObjectRestQueryBuilderMock.query(seriesRestBeanParams) >> dbAstronomicalObjectPage
		1 * pageMapperMock.fromPageToRestResponsePage(dbAstronomicalObjectPage) >> responsePage
		1 * astronomicalObjectRestMapperMock.map(dbAstronomicalObjectList) >> soapAstronomicalObjectList
		astronomicalObjectResponse.astronomicalObjects[0].guid == GUID
		astronomicalObjectResponse.page == responsePage
	}

}
