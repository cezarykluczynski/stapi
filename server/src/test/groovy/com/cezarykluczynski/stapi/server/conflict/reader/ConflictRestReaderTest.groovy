package com.cezarykluczynski.stapi.server.conflict.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictBase
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictFull
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictFullResponse
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.conflict.dto.ConflictRestBeanParams
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictBaseRestMapper
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictFullRestMapper
import com.cezarykluczynski.stapi.server.conflict.query.ConflictRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ConflictRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private ConflictRestQuery conflictRestQueryBuilderMock

	private ConflictBaseRestMapper conflictBaseRestMapperMock

	private ConflictFullRestMapper conflictFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private ConflictRestReader conflictRestReader

	void setup() {
		conflictRestQueryBuilderMock = Mock()
		conflictBaseRestMapperMock = Mock()
		conflictFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		conflictRestReader = new ConflictRestReader(conflictRestQueryBuilderMock, conflictBaseRestMapperMock, conflictFullRestMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		ConflictBase conflictBase = Mock()
		Conflict conflict = Mock()
		ConflictRestBeanParams conflictRestBeanParams = Mock()
		List<ConflictBase> restConflictList = Lists.newArrayList(conflictBase)
		List<Conflict> conflictList = Lists.newArrayList(conflict)
		Page<Conflict> conflictPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		ConflictBaseResponse conflictResponseOutput = conflictRestReader.readBase(conflictRestBeanParams)

		then:
		1 * conflictRestQueryBuilderMock.query(conflictRestBeanParams) >> conflictPage
		1 * pageMapperMock.fromPageToRestResponsePage(conflictPage) >> responsePage
		1 * conflictRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * conflictPage.content >> conflictList
		1 * conflictBaseRestMapperMock.mapBase(conflictList) >> restConflictList
		0 * _
		conflictResponseOutput.conflicts == restConflictList
		conflictResponseOutput.page == responsePage
		conflictResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		ConflictFull conflictFull = Mock()
		Conflict conflict = Mock()
		List<Conflict> conflictList = Lists.newArrayList(conflict)
		Page<Conflict> conflictPage = Mock()

		when:
		ConflictFullResponse conflictResponseOutput = conflictRestReader.readFull(UID)

		then:
		1 * conflictRestQueryBuilderMock.query(_ as ConflictRestBeanParams) >> { ConflictRestBeanParams conflictRestBeanParams ->
			assert conflictRestBeanParams.uid == UID
			conflictPage
		}
		1 * conflictPage.content >> conflictList
		1 * conflictFullRestMapperMock.mapFull(conflict) >> conflictFull
		0 * _
		conflictResponseOutput.conflict == conflictFull
	}

	void "requires UID in full request"() {
		when:
		conflictRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
