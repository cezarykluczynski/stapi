package com.cezarykluczynski.stapi.server.literature.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureBase
import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureFull
import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.literature.entity.Literature
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.literature.dto.LiteratureRestBeanParams
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureBaseRestMapper
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureFullRestMapper
import com.cezarykluczynski.stapi.server.literature.query.LiteratureRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class LiteratureRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private LiteratureRestQuery literatureRestQueryBuilderMock

	private LiteratureBaseRestMapper literatureBaseRestMapperMock

	private LiteratureFullRestMapper literatureFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private LiteratureRestReader literatureRestReader

	void setup() {
		literatureRestQueryBuilderMock = Mock()
		literatureBaseRestMapperMock = Mock()
		literatureFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		literatureRestReader = new LiteratureRestReader(literatureRestQueryBuilderMock, literatureBaseRestMapperMock, literatureFullRestMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		LiteratureBase literatureBase = Mock()
		Literature literature = Mock()
		LiteratureRestBeanParams literatureRestBeanParams = Mock()
		List<LiteratureBase> restLiteratureList = Lists.newArrayList(literatureBase)
		List<Literature> literatureList = Lists.newArrayList(literature)
		Page<Literature> literaturePage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		LiteratureBaseResponse literatureResponseOutput = literatureRestReader.readBase(literatureRestBeanParams)

		then:
		1 * literatureRestQueryBuilderMock.query(literatureRestBeanParams) >> literaturePage
		1 * pageMapperMock.fromPageToRestResponsePage(literaturePage) >> responsePage
		1 * literatureRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * literaturePage.content >> literatureList
		1 * literatureBaseRestMapperMock.mapBase(literatureList) >> restLiteratureList
		0 * _
		literatureResponseOutput.literature == restLiteratureList
		literatureResponseOutput.page == responsePage
		literatureResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		LiteratureFull literatureFull = Mock()
		Literature literature = Mock()
		List<Literature> literatureList = Lists.newArrayList(literature)
		Page<Literature> literaturePage = Mock()

		when:
		LiteratureFullResponse literatureResponseOutput = literatureRestReader.readFull(UID)

		then:
		1 * literatureRestQueryBuilderMock.query(_ as LiteratureRestBeanParams) >> { LiteratureRestBeanParams literatureRestBeanParams ->
			assert literatureRestBeanParams.uid == UID
			literaturePage
		}
		1 * literaturePage.content >> literatureList
		1 * literatureFullRestMapperMock.mapFull(literature) >> literatureFull
		0 * _
		literatureResponseOutput.literature == literatureFull
	}

	void "requires UID in full request"() {
		when:
		literatureRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
