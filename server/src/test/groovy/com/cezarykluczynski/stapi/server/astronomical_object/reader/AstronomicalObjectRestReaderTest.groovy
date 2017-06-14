package com.cezarykluczynski.stapi.server.astronomical_object.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectBase
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectFull
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import com.cezarykluczynski.stapi.server.astronomical_object.dto.AstronomicalObjectRestBeanParams
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectBaseRestMapper
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectFullRestMapper
import com.cezarykluczynski.stapi.server.astronomical_object.query.AstronomicalObjectRestQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class AstronomicalObjectRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private AstronomicalObjectRestQuery astronomicalObjectRestQueryBuilderMock

	private AstronomicalObjectBaseRestMapper astronomicalObjectBaseRestMapperMock

	private AstronomicalObjectFullRestMapper astronomicalObjectFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private AstronomicalObjectRestReader astronomicalObjectRestReader

	void setup() {
		astronomicalObjectRestQueryBuilderMock = Mock()
		astronomicalObjectBaseRestMapperMock = Mock()
		astronomicalObjectFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		astronomicalObjectRestReader = new AstronomicalObjectRestReader(astronomicalObjectRestQueryBuilderMock, astronomicalObjectBaseRestMapperMock,
				astronomicalObjectFullRestMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		AstronomicalObjectBase astronomicalObjectBase = Mock()
		AstronomicalObject astronomicalObject = Mock()
		AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams = Mock()
		List<AstronomicalObjectBase> restAstronomicalObjectList = Lists.newArrayList(astronomicalObjectBase)
		List<AstronomicalObject> astronomicalObjectList = Lists.newArrayList(astronomicalObject)
		Page<AstronomicalObject> astronomicalObjectPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		AstronomicalObjectBaseResponse astronomicalObjectResponseOutput = astronomicalObjectRestReader.readBase(astronomicalObjectRestBeanParams)

		then:
		1 * astronomicalObjectRestQueryBuilderMock.query(astronomicalObjectRestBeanParams) >> astronomicalObjectPage
		1 * pageMapperMock.fromPageToRestResponsePage(astronomicalObjectPage) >> responsePage
		1 * astronomicalObjectRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * astronomicalObjectPage.content >> astronomicalObjectList
		1 * astronomicalObjectBaseRestMapperMock.mapBase(astronomicalObjectList) >> restAstronomicalObjectList
		0 * _
		astronomicalObjectResponseOutput.astronomicalObjects == restAstronomicalObjectList
		astronomicalObjectResponseOutput.page == responsePage
		astronomicalObjectResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		AstronomicalObjectFull astronomicalObjectFull = Mock()
		AstronomicalObject astronomicalObject = Mock()
		List<AstronomicalObject> astronomicalObjectList = Lists.newArrayList(astronomicalObject)
		Page<AstronomicalObject> astronomicalObjectPage = Mock()

		when:
		AstronomicalObjectFullResponse astronomicalObjectResponseOutput = astronomicalObjectRestReader.readFull(UID)

		then:
		1 * astronomicalObjectRestQueryBuilderMock.query(_ as AstronomicalObjectRestBeanParams) >> {
				AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams ->
			assert astronomicalObjectRestBeanParams.uid == UID
			astronomicalObjectPage
		}
		1 * astronomicalObjectPage.content >> astronomicalObjectList
		1 * astronomicalObjectFullRestMapperMock.mapFull(astronomicalObject) >> astronomicalObjectFull
		0 * _
		astronomicalObjectResponseOutput.astronomicalObject == astronomicalObjectFull
	}

	void "requires UID in full request"() {
		when:
		astronomicalObjectRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
