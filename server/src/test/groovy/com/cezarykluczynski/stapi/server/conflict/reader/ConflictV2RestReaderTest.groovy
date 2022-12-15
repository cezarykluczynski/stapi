package com.cezarykluczynski.stapi.server.conflict.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictV2Full
import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictV2FullResponse
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.conflict.dto.ConflictRestBeanParams
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictFullRestMapper
import com.cezarykluczynski.stapi.server.conflict.query.ConflictRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ConflictV2RestReaderTest extends Specification {

	private static final String UID = 'UID'

	private ConflictRestQuery conflictRestQueryBuilderMock

	private ConflictFullRestMapper conflictFullRestMapperMock

	private ConflictV2RestReader conflictV2RestReader

	void setup() {
		conflictRestQueryBuilderMock = Mock()
		conflictFullRestMapperMock = Mock()
		conflictV2RestReader = new ConflictV2RestReader(conflictRestQueryBuilderMock, conflictFullRestMapperMock,)
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		ConflictV2Full conflictV2Full = Mock()
		Conflict conflict = Mock()
		List<Conflict> conflictList = Lists.newArrayList(conflict)
		Page<Conflict> conflictPage = Mock()

		when:
		ConflictV2FullResponse conflictResponseOutput = conflictV2RestReader.readFull(UID)

		then:
		1 * conflictRestQueryBuilderMock.query(_ as ConflictRestBeanParams) >> { ConflictRestBeanParams conflictRestBeanParams ->
			assert conflictRestBeanParams.uid == UID
			conflictPage
		}
		1 * conflictPage.content >> conflictList
		1 * conflictFullRestMapperMock.mapV2Full(conflict) >> conflictV2Full
		0 * _
		conflictResponseOutput.conflict == conflictV2Full
	}

	void "requires UID in full request"() {
		when:
		conflictV2RestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
