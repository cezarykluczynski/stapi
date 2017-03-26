package com.cezarykluczynski.stapi.server.astronomicalObject.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectBase
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectFull
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject
import com.cezarykluczynski.stapi.server.astronomicalObject.dto.AstronomicalObjectRestBeanParams
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectBaseRestMapper
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectFullRestMapper
import com.cezarykluczynski.stapi.server.astronomicalObject.query.AstronomicalObjectRestQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class AstronomicalObjectRestReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private AstronomicalObjectRestQuery astronomicalObjectRestQueryBuilderMock

	private AstronomicalObjectBaseRestMapper astronomicalObjectBaseRestMapperMock

	private AstronomicalObjectFullRestMapper astronomicalObjectFullRestMapperMock

	private PageMapper pageMapperMock

	private AstronomicalObjectRestReader astronomicalObjectRestReader

	void setup() {
		astronomicalObjectRestQueryBuilderMock = Mock(AstronomicalObjectRestQuery)
		astronomicalObjectBaseRestMapperMock = Mock(AstronomicalObjectBaseRestMapper)
		astronomicalObjectFullRestMapperMock = Mock(AstronomicalObjectFullRestMapper)
		pageMapperMock = Mock(PageMapper)
		astronomicalObjectRestReader = new AstronomicalObjectRestReader(astronomicalObjectRestQueryBuilderMock, astronomicalObjectBaseRestMapperMock,
				astronomicalObjectFullRestMapperMock, pageMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams = Mock(AstronomicalObjectRestBeanParams)
		List<AstronomicalObjectBase> restAstronomicalObjectList = Lists.newArrayList(Mock(AstronomicalObjectBase))
		List<AstronomicalObject> astronomicalObjectList = Lists.newArrayList(Mock(AstronomicalObject))
		Page<AstronomicalObject> astronomicalObjectPage = Mock(Page)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		AstronomicalObjectBaseResponse astronomicalObjectResponseOutput = astronomicalObjectRestReader.readBase(astronomicalObjectRestBeanParams)

		then:
		1 * astronomicalObjectRestQueryBuilderMock.query(astronomicalObjectRestBeanParams) >> astronomicalObjectPage
		1 * pageMapperMock.fromPageToRestResponsePage(astronomicalObjectPage) >> responsePage
		1 * astronomicalObjectPage.content >> astronomicalObjectList
		1 * astronomicalObjectBaseRestMapperMock.mapBase(astronomicalObjectList) >> restAstronomicalObjectList
		0 * _
		astronomicalObjectResponseOutput.astronomicalObjects == restAstronomicalObjectList
		astronomicalObjectResponseOutput.page == responsePage
	}

	void "passed GUID to queryBuilder, then to mapper, and returns result"() {
		given:
		AstronomicalObjectFull astronomicalObjectFull = Mock(AstronomicalObjectFull)
		AstronomicalObject astronomicalObject = Mock(AstronomicalObject)
		List<AstronomicalObject> astronomicalObjectList = Lists.newArrayList(astronomicalObject)
		Page<AstronomicalObject> astronomicalObjectPage = Mock(Page)

		when:
		AstronomicalObjectFullResponse astronomicalObjectResponseOutput = astronomicalObjectRestReader.readFull(GUID)

		then:
		1 * astronomicalObjectRestQueryBuilderMock.query(_ as AstronomicalObjectRestBeanParams) >> {
				AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams ->
			assert astronomicalObjectRestBeanParams.guid == GUID
			astronomicalObjectPage
		}
		1 * astronomicalObjectPage.content >> astronomicalObjectList
		1 * astronomicalObjectFullRestMapperMock.mapFull(astronomicalObject) >> astronomicalObjectFull
		0 * _
		astronomicalObjectResponseOutput.astronomicalObject == astronomicalObjectFull
	}

}
