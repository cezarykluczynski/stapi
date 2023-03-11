package com.cezarykluczynski.stapi.server.technology.reader

import com.cezarykluczynski.stapi.client.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.rest.model.TechnologyV2Base
import com.cezarykluczynski.stapi.client.rest.model.TechnologyV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.TechnologyV2Full
import com.cezarykluczynski.stapi.client.rest.model.TechnologyV2FullResponse
import com.cezarykluczynski.stapi.model.technology.entity.Technology
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.technology.dto.TechnologyV2RestBeanParams
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyBaseRestMapper
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyFullRestMapper
import com.cezarykluczynski.stapi.server.technology.query.TechnologyRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class TechnologyV2RestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private TechnologyRestQuery technologyRestQueryBuilderMock

	private TechnologyBaseRestMapper technologyBaseRestMapperMock

	private TechnologyFullRestMapper technologyFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private TechnologyV2RestReader technologyV2RestReader

	void setup() {
		technologyRestQueryBuilderMock = Mock()
		technologyBaseRestMapperMock = Mock()
		technologyFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		technologyV2RestReader = new TechnologyV2RestReader(technologyRestQueryBuilderMock, technologyBaseRestMapperMock,
				technologyFullRestMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		TechnologyV2Base technologyV2Base = Mock()
		Technology technology = Mock()
		TechnologyV2RestBeanParams technologyV2RestBeanParams = Mock()
		List<TechnologyV2Base> restTechnologyList = Lists.newArrayList(technologyV2Base)
		List<Technology> technologyList = Lists.newArrayList(technology)
		Page<Technology> technologyPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		TechnologyV2BaseResponse technologyResponseOutput = technologyV2RestReader.readBase(technologyV2RestBeanParams)

		then:
		1 * technologyRestQueryBuilderMock.query(technologyV2RestBeanParams) >> technologyPage
		1 * pageMapperMock.fromPageToRestResponsePage(technologyPage) >> responsePage
		1 * technologyV2RestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * technologyPage.content >> technologyList
		1 * technologyBaseRestMapperMock.mapV2Base(technologyList) >> restTechnologyList
		0 * _
		technologyResponseOutput.technology == restTechnologyList
		technologyResponseOutput.page == responsePage
		technologyResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		TechnologyV2Full technologyV2Full = Mock()
		Technology technology = Mock()
		List<Technology> technologyList = Lists.newArrayList(technology)
		Page<Technology> technologyPage = Mock()

		when:
		TechnologyV2FullResponse technologyResponseOutput = technologyV2RestReader.readFull(UID)

		then:
		1 * technologyRestQueryBuilderMock.query(_ as TechnologyV2RestBeanParams) >> { TechnologyV2RestBeanParams technologyV2RestBeanParams ->
			assert technologyV2RestBeanParams.uid == UID
			technologyPage
		}
		1 * technologyPage.content >> technologyList
		1 * technologyFullRestMapperMock.mapV2Full(technology) >> technologyV2Full
		0 * _
		technologyResponseOutput.technology == technologyV2Full
	}

	void "requires UID in full request"() {
		when:
		technologyV2RestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
