package com.cezarykluczynski.stapi.server.astronomical_object.reader

import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2Base
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2Full
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2FullResponse
import com.cezarykluczynski.stapi.client.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import com.cezarykluczynski.stapi.server.astronomical_object.dto.AstronomicalObjectRestBeanParams
import com.cezarykluczynski.stapi.server.astronomical_object.dto.AstronomicalObjectV2RestBeanParams
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectBaseRestMapper
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectFullRestMapper
import com.cezarykluczynski.stapi.server.astronomical_object.query.AstronomicalObjectRestQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class AstronomicalObjectV2RestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private AstronomicalObjectRestQuery astronomicalObjectRestQueryBuilderMock

	private AstronomicalObjectBaseRestMapper astronomicalObjectBaseRestMapperMock

	private AstronomicalObjectFullRestMapper astronomicalObjectFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private AstronomicalObjectV2RestReader astronomicalObjectV2RestReader

	void setup() {
		astronomicalObjectRestQueryBuilderMock = Mock()
		astronomicalObjectBaseRestMapperMock = Mock()
		astronomicalObjectFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		astronomicalObjectV2RestReader = new AstronomicalObjectV2RestReader(astronomicalObjectRestQueryBuilderMock,
				astronomicalObjectBaseRestMapperMock, astronomicalObjectFullRestMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		AstronomicalObjectV2Base astronomicalObjectV2Base = Mock()
		AstronomicalObject astronomicalObject = Mock()
		AstronomicalObjectV2RestBeanParams astronomicalObjectV2RestBeanParams = Mock()
		List<AstronomicalObjectV2Base> restAstronomicalObjectList = Lists.newArrayList(astronomicalObjectV2Base)
		List<AstronomicalObject> astronomicalObjectList = Lists.newArrayList(astronomicalObject)
		Page<AstronomicalObject> astronomicalObjectPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		AstronomicalObjectV2BaseResponse astronomicalObjectV2ResponseOutput = astronomicalObjectV2RestReader.readBase(astronomicalObjectV2RestBeanParams)

		then:
		1 * astronomicalObjectRestQueryBuilderMock.query(astronomicalObjectV2RestBeanParams) >> astronomicalObjectPage
		1 * pageMapperMock.fromPageToRestResponsePage(astronomicalObjectPage) >> responsePage
		1 * astronomicalObjectV2RestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * astronomicalObjectPage.content >> astronomicalObjectList
		1 * astronomicalObjectBaseRestMapperMock.mapV2Base(astronomicalObjectList) >> restAstronomicalObjectList
		0 * _
		astronomicalObjectV2ResponseOutput.astronomicalObjects == restAstronomicalObjectList
		astronomicalObjectV2ResponseOutput.page == responsePage
		astronomicalObjectV2ResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		AstronomicalObjectV2Full astronomicalObjectV2Full = Mock()
		AstronomicalObject astronomicalObject = Mock()
		List<AstronomicalObject> astronomicalObjectList = Lists.newArrayList(astronomicalObject)
		Page<AstronomicalObject> astronomicalObjectPage = Mock()

		when:
		AstronomicalObjectV2FullResponse astronomicalObjectResponseOutput = astronomicalObjectV2RestReader.readFull(UID)

		then:
		1 * astronomicalObjectRestQueryBuilderMock.query(_ as AstronomicalObjectRestBeanParams) >> {
			AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams ->
				assert astronomicalObjectRestBeanParams.uid == UID
				astronomicalObjectPage
		}
		1 * astronomicalObjectPage.content >> astronomicalObjectList
		1 * astronomicalObjectFullRestMapperMock.mapV2Full(astronomicalObject) >> astronomicalObjectV2Full
		0 * _
		astronomicalObjectResponseOutput.astronomicalObject == astronomicalObjectV2Full
	}

	void "requires UID in full request"() {
		when:
		astronomicalObjectV2RestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
