package com.cezarykluczynski.stapi.server.performer.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerBase
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerFull
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseRestMapper
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerFullRestMapper
import com.cezarykluczynski.stapi.server.performer.query.PerformerRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class PerformerRestReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private PerformerRestQuery performerRestQueryBuilderMock

	private PerformerBaseRestMapper performerBaseRestMapperMock

	private PerformerFullRestMapper performerFullRestMapperMock

	private PageMapper pageMapperMock

	private PerformerRestReader performerRestReader

	void setup() {
		performerRestQueryBuilderMock = Mock(PerformerRestQuery)
		performerBaseRestMapperMock = Mock(PerformerBaseRestMapper)
		performerFullRestMapperMock = Mock(PerformerFullRestMapper)
		pageMapperMock = Mock(PageMapper)
		performerRestReader = new PerformerRestReader(performerRestQueryBuilderMock, performerBaseRestMapperMock, performerFullRestMapperMock,
				pageMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		PerformerRestBeanParams performerRestBeanParams = Mock(PerformerRestBeanParams)
		List<PerformerBase> restPerformerList = Lists.newArrayList(Mock(PerformerBase))
		List<Performer> performerList = Lists.newArrayList(Mock(Performer))
		Page<Performer> performerPage = Mock(Page)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		PerformerBaseResponse performerResponseOutput = performerRestReader.readBase(performerRestBeanParams)

		then:
		1 * performerRestQueryBuilderMock.query(performerRestBeanParams) >> performerPage
		1 * pageMapperMock.fromPageToRestResponsePage(performerPage) >> responsePage
		1 * performerPage.content >> performerList
		1 * performerBaseRestMapperMock.mapBase(performerList) >> restPerformerList
		0 * _
		performerResponseOutput.performers == restPerformerList
		performerResponseOutput.page == responsePage
	}

	void "passed GUID to queryBuilder, then to mapper, and returns result"() {
		given:
		PerformerFull performerFull = Mock(PerformerFull)
		Performer performer = Mock(Performer)
		List<Performer> performerList = Lists.newArrayList(performer)
		Page<Performer> performerPage = Mock(Page)

		when:
		PerformerFullResponse performerResponseOutput = performerRestReader.readFull(GUID)

		then:
		1 * performerRestQueryBuilderMock.query(_ as PerformerRestBeanParams) >> { PerformerRestBeanParams performerRestBeanParams ->
			assert performerRestBeanParams.guid == GUID
			performerPage
		}
		1 * performerPage.content >> performerList
		1 * performerFullRestMapperMock.mapFull(performer) >> performerFull
		0 * _
		performerResponseOutput.performer == performerFull
	}

}
