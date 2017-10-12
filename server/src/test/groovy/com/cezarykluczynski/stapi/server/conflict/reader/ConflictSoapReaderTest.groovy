package com.cezarykluczynski.stapi.server.conflict.reader

import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.client.v1.soap.ConflictBase
import com.cezarykluczynski.stapi.client.v1.soap.ConflictBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ConflictBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ConflictFull
import com.cezarykluczynski.stapi.client.v1.soap.ConflictFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ConflictFullResponse
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictBaseSoapMapper
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictFullSoapMapper
import com.cezarykluczynski.stapi.server.conflict.query.ConflictSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ConflictSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private ConflictSoapQuery conflictSoapQueryBuilderMock

	private ConflictBaseSoapMapper conflictBaseSoapMapperMock

	private ConflictFullSoapMapper conflictFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private ConflictSoapReader conflictSoapReader

	void setup() {
		conflictSoapQueryBuilderMock = Mock()
		conflictBaseSoapMapperMock = Mock()
		conflictFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		conflictSoapReader = new ConflictSoapReader(conflictSoapQueryBuilderMock, conflictBaseSoapMapperMock, conflictFullSoapMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Conflict> conflictList = Lists.newArrayList()
		Page<Conflict> conflictPage = Mock()
		List<ConflictBase> soapConflictList = Lists.newArrayList(new ConflictBase(uid: UID))
		ConflictBaseRequest conflictBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		ConflictBaseResponse conflictResponse = conflictSoapReader.readBase(conflictBaseRequest)

		then:
		1 * conflictSoapQueryBuilderMock.query(conflictBaseRequest) >> conflictPage
		1 * conflictPage.content >> conflictList
		1 * pageMapperMock.fromPageToSoapResponsePage(conflictPage) >> responsePage
		1 * conflictBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * conflictBaseSoapMapperMock.mapBase(conflictList) >> soapConflictList
		0 * _
		conflictResponse.conflicts[0].uid == UID
		conflictResponse.page == responsePage
		conflictResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		ConflictFull conflictFull = new ConflictFull(uid: UID)
		Conflict conflict = Mock()
		Page<Conflict> conflictPage = Mock()
		ConflictFullRequest conflictFullRequest = new ConflictFullRequest(uid: UID)

		when:
		ConflictFullResponse conflictFullResponse = conflictSoapReader.readFull(conflictFullRequest)

		then:
		1 * conflictSoapQueryBuilderMock.query(conflictFullRequest) >> conflictPage
		1 * conflictPage.content >> Lists.newArrayList(conflict)
		1 * conflictFullSoapMapperMock.mapFull(conflict) >> conflictFull
		0 * _
		conflictFullResponse.conflict.uid == UID
	}

	void "requires UID in full request"() {
		given:
		ConflictFullRequest conflictFullRequest = Mock()

		when:
		conflictSoapReader.readFull(conflictFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
