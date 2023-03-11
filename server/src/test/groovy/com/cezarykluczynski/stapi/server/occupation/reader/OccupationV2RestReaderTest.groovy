package com.cezarykluczynski.stapi.server.occupation.reader

import com.cezarykluczynski.stapi.client.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.rest.model.OccupationV2Base
import com.cezarykluczynski.stapi.client.rest.model.OccupationV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.OccupationV2Full
import com.cezarykluczynski.stapi.client.rest.model.OccupationV2FullResponse
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.occupation.dto.OccupationV2RestBeanParams
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationBaseRestMapper
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationFullRestMapper
import com.cezarykluczynski.stapi.server.occupation.query.OccupationRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class OccupationV2RestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private OccupationRestQuery occupationRestQueryBuilderMock

	private OccupationBaseRestMapper occupationBaseRestMapperMock

	private OccupationFullRestMapper occupationFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private OccupationV2RestReader occupationV2RestReader

	void setup() {
		occupationRestQueryBuilderMock = Mock()
		occupationBaseRestMapperMock = Mock()
		occupationFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		occupationV2RestReader = new OccupationV2RestReader(occupationRestQueryBuilderMock, occupationBaseRestMapperMock,
				occupationFullRestMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		OccupationV2Base occupationV2Base = Mock()
		Occupation occupation = Mock()
		OccupationV2RestBeanParams occupationV2RestBeanParams = Mock()
		List<OccupationV2Base> restOccupationList = Lists.newArrayList(occupationV2Base)
		List<Occupation> occupationList = Lists.newArrayList(occupation)
		Page<Occupation> occupationPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		OccupationV2BaseResponse occupationResponseOutput = occupationV2RestReader.readBase(occupationV2RestBeanParams)

		then:
		1 * occupationRestQueryBuilderMock.query(occupationV2RestBeanParams) >> occupationPage
		1 * pageMapperMock.fromPageToRestResponsePage(occupationPage) >> responsePage
		1 * occupationV2RestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * occupationPage.content >> occupationList
		1 * occupationBaseRestMapperMock.mapV2Base(occupationList) >> restOccupationList
		0 * _
		occupationResponseOutput.occupations == restOccupationList
		occupationResponseOutput.page == responsePage
		occupationResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		OccupationV2Full occupationV2Full = Mock()
		Occupation occupation = Mock()
		List<Occupation> occupationList = Lists.newArrayList(occupation)
		Page<Occupation> occupationPage = Mock()

		when:
		OccupationV2FullResponse occupationResponseOutput = occupationV2RestReader.readFull(UID)

		then:
		1 * occupationRestQueryBuilderMock.query(_ as OccupationV2RestBeanParams) >> { OccupationV2RestBeanParams occupationV2RestBeanParams ->
			assert occupationV2RestBeanParams.uid == UID
			occupationPage
		}
		1 * occupationPage.content >> occupationList
		1 * occupationFullRestMapperMock.mapV2Full(occupation) >> occupationV2Full
		0 * _
		occupationResponseOutput.occupation == occupationV2Full
	}

	void "requires UID in full request"() {
		when:
		occupationV2RestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
