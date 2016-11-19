package com.cezarykluczynski.stapi.server.performer.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.Performer as RESTPerformer
import com.cezarykluczynski.stapi.client.v1.rest.model.PerformerResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerRestMapper
import com.cezarykluczynski.stapi.server.performer.query.PerformerRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class PerformerRestReaderTest extends Specification {

	private static final Long ID = 1L

	private PerformerRestQuery performerRestQueryBuilderMock

	private PerformerRestMapper performerRestMapperMock

	private PageMapper pageMapperMock

	private PerformerRestReader performerRestReader

	def setup() {
		performerRestQueryBuilderMock = Mock(PerformerRestQuery)
		performerRestMapperMock = Mock(PerformerRestMapper)
		pageMapperMock = Mock(PageMapper)
		performerRestReader = new PerformerRestReader(performerRestQueryBuilderMock, performerRestMapperMock, pageMapperMock)
	}

	def "gets database entities and puts them into PerformerResponse"() {
		given:
		List<Performer> dbPerformerList = Lists.newArrayList()
		Page<Performer> dbPerformerPage = Mock(Page) {
			getContent() >> dbPerformerList
		}
		List<RESTPerformer> soapPerformerList = Lists.newArrayList(new RESTPerformer(id: ID))
		PerformerRestBeanParams seriesRestBeanParams = Mock(PerformerRestBeanParams)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		PerformerResponse performerResponse = performerRestReader.read(seriesRestBeanParams)

		then:
		1 * performerRestQueryBuilderMock.query(seriesRestBeanParams) >> dbPerformerPage
		1 * pageMapperMock.fromPageToRestResponsePage(dbPerformerPage) >> responsePage
		1 * performerRestMapperMock.map(dbPerformerList) >> soapPerformerList
		performerResponse.performers[0].id == ID
		performerResponse.page == responsePage
	}

}
