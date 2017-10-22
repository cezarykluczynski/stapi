package com.cezarykluczynski.stapi.server.occupation.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationBase
import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationFull
import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.occupation.dto.OccupationRestBeanParams
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationBaseRestMapper
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationFullRestMapper
import com.cezarykluczynski.stapi.server.occupation.query.OccupationRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class OccupationRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private OccupationRestQuery occupationRestQueryBuilderMock

	private OccupationBaseRestMapper occupationBaseRestMapperMock

	private OccupationFullRestMapper occupationFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private OccupationRestReader occupationRestReader

	void setup() {
		occupationRestQueryBuilderMock = Mock()
		occupationBaseRestMapperMock = Mock()
		occupationFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		occupationRestReader = new OccupationRestReader(occupationRestQueryBuilderMock, occupationBaseRestMapperMock, occupationFullRestMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		OccupationBase occupationBase = Mock()
		Occupation occupation = Mock()
		OccupationRestBeanParams occupationRestBeanParams = Mock()
		List<OccupationBase> restOccupationList = Lists.newArrayList(occupationBase)
		List<Occupation> occupationList = Lists.newArrayList(occupation)
		Page<Occupation> occupationPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		OccupationBaseResponse occupationResponseOutput = occupationRestReader.readBase(occupationRestBeanParams)

		then:
		1 * occupationRestQueryBuilderMock.query(occupationRestBeanParams) >> occupationPage
		1 * pageMapperMock.fromPageToRestResponsePage(occupationPage) >> responsePage
		1 * occupationRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * occupationPage.content >> occupationList
		1 * occupationBaseRestMapperMock.mapBase(occupationList) >> restOccupationList
		0 * _
		occupationResponseOutput.occupations == restOccupationList
		occupationResponseOutput.page == responsePage
		occupationResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		OccupationFull occupationFull = Mock()
		Occupation occupation = Mock()
		List<Occupation> occupationList = Lists.newArrayList(occupation)
		Page<Occupation> occupationPage = Mock()

		when:
		OccupationFullResponse occupationResponseOutput = occupationRestReader.readFull(UID)

		then:
		1 * occupationRestQueryBuilderMock.query(_ as OccupationRestBeanParams) >> { OccupationRestBeanParams occupationRestBeanParams ->
			assert occupationRestBeanParams.uid == UID
			occupationPage
		}
		1 * occupationPage.content >> occupationList
		1 * occupationFullRestMapperMock.mapFull(occupation) >> occupationFull
		0 * _
		occupationResponseOutput.occupation == occupationFull
	}

	void "requires UID in full request"() {
		when:
		occupationRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
