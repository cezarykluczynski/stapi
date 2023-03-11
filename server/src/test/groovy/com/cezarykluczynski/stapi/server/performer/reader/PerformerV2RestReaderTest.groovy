package com.cezarykluczynski.stapi.server.performer.reader

import com.cezarykluczynski.stapi.client.rest.model.PerformerV2Base
import com.cezarykluczynski.stapi.client.rest.model.PerformerV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.PerformerV2Full
import com.cezarykluczynski.stapi.client.rest.model.PerformerV2FullResponse
import com.cezarykluczynski.stapi.client.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.performer.dto.PerformerV2RestBeanParams
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseRestMapper
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerFullRestMapper
import com.cezarykluczynski.stapi.server.performer.query.PerformerRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class PerformerV2RestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private PerformerRestQuery performerRestQueryBuilderMock

	private PerformerBaseRestMapper performerBaseRestMapperMock

	private PerformerFullRestMapper performerFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private PerformerV2RestReader performerV2RestReader

	void setup() {
		performerRestQueryBuilderMock = Mock()
		performerBaseRestMapperMock = Mock()
		performerFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		performerV2RestReader = new PerformerV2RestReader(performerRestQueryBuilderMock, performerBaseRestMapperMock, performerFullRestMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		PerformerV2Base performerBase = Mock()
		Performer performer = Mock()
		PerformerV2RestBeanParams performerV2RestBeanParams = Mock()
		List<PerformerV2Base> restPerformerList = Lists.newArrayList(performerBase)
		List<Performer> performerList = Lists.newArrayList(performer)
		Page<Performer> performerPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		PerformerV2BaseResponse performerV2ResponseOutput = performerV2RestReader.readBase(performerV2RestBeanParams)

		then:
		1 * performerRestQueryBuilderMock.query(performerV2RestBeanParams) >> performerPage
		1 * pageMapperMock.fromPageToRestResponsePage(performerPage) >> responsePage
		1 * performerV2RestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * performerPage.content >> performerList
		1 * performerBaseRestMapperMock.mapV2Base(performerList) >> restPerformerList
		0 * _
		performerV2ResponseOutput.performers == restPerformerList
		performerV2ResponseOutput.page == responsePage
		performerV2ResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		PerformerV2Full performerV2Full = Mock()
		Performer performer = Mock()
		List<Performer> performerList = Lists.newArrayList(performer)
		Page<Performer> performerPage = Mock()

		when:
		PerformerV2FullResponse performerV2ResponseOutput = performerV2RestReader.readFull(UID)

		then:
		1 * performerRestQueryBuilderMock.query(_ as PerformerV2RestBeanParams) >> { PerformerV2RestBeanParams performerV2RestBeanParams ->
			assert performerV2RestBeanParams.uid == UID
			performerPage
		}
		1 * performerPage.content >> performerList
		1 * performerFullRestMapperMock.mapV2Full(performer) >> performerV2Full
		0 * _
		performerV2ResponseOutput.performer == performerV2Full
	}

	void "requires UID in full request"() {
		when:
		performerV2RestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
