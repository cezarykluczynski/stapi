package com.cezarykluczynski.stapi.server.technology.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyBase
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyFull
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.technology.entity.Technology
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.technology.dto.TechnologyRestBeanParams
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyBaseRestMapper
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyFullRestMapper
import com.cezarykluczynski.stapi.server.technology.query.TechnologyRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class TechnologyRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private TechnologyRestQuery technologyRestQueryBuilderMock

	private TechnologyBaseRestMapper technologyBaseRestMapperMock

	private TechnologyFullRestMapper technologyFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private TechnologyRestReader technologyRestReader

	void setup() {
		technologyRestQueryBuilderMock = Mock()
		technologyBaseRestMapperMock = Mock()
		technologyFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		technologyRestReader = new TechnologyRestReader(technologyRestQueryBuilderMock, technologyBaseRestMapperMock, technologyFullRestMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		TechnologyBase technologyBase = Mock()
		Technology technology = Mock()
		TechnologyRestBeanParams technologyRestBeanParams = Mock()
		List<TechnologyBase> restTechnologyList = Lists.newArrayList(technologyBase)
		List<Technology> technologyList = Lists.newArrayList(technology)
		Page<Technology> technologyPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		TechnologyBaseResponse technologyResponseOutput = technologyRestReader.readBase(technologyRestBeanParams)

		then:
		1 * technologyRestQueryBuilderMock.query(technologyRestBeanParams) >> technologyPage
		1 * pageMapperMock.fromPageToRestResponsePage(technologyPage) >> responsePage
		1 * technologyRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * technologyPage.content >> technologyList
		1 * technologyBaseRestMapperMock.mapBase(technologyList) >> restTechnologyList
		0 * _
		technologyResponseOutput.technology == restTechnologyList
		technologyResponseOutput.page == responsePage
		technologyResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		TechnologyFull technologyFull = Mock()
		Technology technology = Mock()
		List<Technology> technologyList = Lists.newArrayList(technology)
		Page<Technology> technologyPage = Mock()

		when:
		TechnologyFullResponse technologyResponseOutput = technologyRestReader.readFull(UID)

		then:
		1 * technologyRestQueryBuilderMock.query(_ as TechnologyRestBeanParams) >> { TechnologyRestBeanParams technologyRestBeanParams ->
			assert technologyRestBeanParams.uid == UID
			technologyPage
		}
		1 * technologyPage.content >> technologyList
		1 * technologyFullRestMapperMock.mapFull(technology) >> technologyFull
		0 * _
		technologyResponseOutput.technology == technologyFull
	}

	void "requires UID in full request"() {
		when:
		technologyRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
