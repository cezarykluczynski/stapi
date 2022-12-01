package com.cezarykluczynski.stapi.server.location.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.LocationV2Base
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationV2Full
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationV2FullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.location.entity.Location
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.location.dto.LocationV2RestBeanParams
import com.cezarykluczynski.stapi.server.location.mapper.LocationBaseRestMapper
import com.cezarykluczynski.stapi.server.location.mapper.LocationFullRestMapper
import com.cezarykluczynski.stapi.server.location.query.LocationRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class LocationV2RestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private LocationRestQuery locationRestQueryBuilderMock

	private LocationBaseRestMapper locationBaseRestMapperMock

	private LocationFullRestMapper locationFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private LocationV2RestReader locationV2RestReader

	void setup() {
		locationRestQueryBuilderMock = Mock()
		locationBaseRestMapperMock = Mock()
		locationFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		locationV2RestReader = new LocationV2RestReader(locationRestQueryBuilderMock, locationBaseRestMapperMock, locationFullRestMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		LocationV2Base locationV2Base = Mock()
		Location location = Mock()
		LocationV2RestBeanParams locationV2RestBeanParams = Mock()
		List<LocationV2Base> restLocationList = Lists.newArrayList(locationV2Base)
		List<Location> locationList = Lists.newArrayList(location)
		Page<Location> locationPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		LocationV2BaseResponse locationResponseOutput = locationV2RestReader.readBase(locationV2RestBeanParams)

		then:
		1 * locationRestQueryBuilderMock.query(locationV2RestBeanParams) >> locationPage
		1 * pageMapperMock.fromPageToRestResponsePage(locationPage) >> responsePage
		1 * locationV2RestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * locationPage.content >> locationList
		1 * locationBaseRestMapperMock.mapV2Base(locationList) >> restLocationList
		0 * _
		locationResponseOutput.locations == restLocationList
		locationResponseOutput.page == responsePage
		locationResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		LocationV2Full locationV2Full = Mock()
		Location location = Mock()
		List<Location> locationList = Lists.newArrayList(location)
		Page<Location> locationPage = Mock()

		when:
		LocationV2FullResponse locationResponseOutput = locationV2RestReader.readFull(UID)

		then:
		1 * locationRestQueryBuilderMock.query(_ as LocationV2RestBeanParams) >> { LocationV2RestBeanParams locationV2RestBeanParams ->
			assert locationV2RestBeanParams.uid == UID
			locationPage
		}
		1 * locationPage.content >> locationList
		1 * locationFullRestMapperMock.mapV2Full(location) >> locationV2Full
		0 * _
		locationResponseOutput.location == locationV2Full
	}

	void "requires UID in full request"() {
		when:
		locationV2RestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
