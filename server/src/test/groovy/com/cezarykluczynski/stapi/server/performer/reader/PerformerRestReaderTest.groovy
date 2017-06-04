package com.cezarykluczynski.stapi.server.performer.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerBase
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerFull
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseRestMapper
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerFullRestMapper
import com.cezarykluczynski.stapi.server.performer.query.PerformerRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class PerformerRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private PerformerRestQuery performerRestQueryBuilderMock

	private PerformerBaseRestMapper performerBaseRestMapperMock

	private PerformerFullRestMapper performerFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private PerformerRestReader performerRestReader

	void setup() {
		performerRestQueryBuilderMock = Mock()
		performerBaseRestMapperMock = Mock()
		performerFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		performerRestReader = new PerformerRestReader(performerRestQueryBuilderMock, performerBaseRestMapperMock, performerFullRestMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		PerformerBase performerBase = Mock()
		Performer performer = Mock()
		PerformerRestBeanParams performerRestBeanParams = Mock()
		List<PerformerBase> restPerformerList = Lists.newArrayList(performerBase)
		List<Performer> performerList = Lists.newArrayList(performer)
		Page<Performer> performerPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		PerformerBaseResponse performerResponseOutput = performerRestReader.readBase(performerRestBeanParams)

		then:
		1 * performerRestQueryBuilderMock.query(performerRestBeanParams) >> performerPage
		1 * pageMapperMock.fromPageToRestResponsePage(performerPage) >> responsePage
		1 * performerRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * performerPage.content >> performerList
		1 * performerBaseRestMapperMock.mapBase(performerList) >> restPerformerList
		0 * _
		performerResponseOutput.performers == restPerformerList
		performerResponseOutput.page == responsePage
		performerResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		PerformerFull performerFull = Mock()
		Performer performer = Mock()
		List<Performer> performerList = Lists.newArrayList(performer)
		Page<Performer> performerPage = Mock()

		when:
		PerformerFullResponse performerResponseOutput = performerRestReader.readFull(UID)

		then:
		1 * performerRestQueryBuilderMock.query(_ as PerformerRestBeanParams) >> { PerformerRestBeanParams performerRestBeanParams ->
			assert performerRestBeanParams.uid == UID
			performerPage
		}
		1 * performerPage.content >> performerList
		1 * performerFullRestMapperMock.mapFull(performer) >> performerFull
		0 * _
		performerResponseOutput.performer == performerFull
	}

	void "requires UID in full request"() {
		when:
		performerRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
