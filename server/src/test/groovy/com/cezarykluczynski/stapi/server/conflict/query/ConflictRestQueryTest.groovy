package com.cezarykluczynski.stapi.server.conflict.query

import com.cezarykluczynski.stapi.model.conflict.dto.ConflictRequestDTO
import com.cezarykluczynski.stapi.model.conflict.repository.ConflictRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.conflict.dto.ConflictRestBeanParams
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class ConflictRestQueryTest extends Specification {

	private ConflictBaseRestMapper conflictBaseRestMapperMock

	private PageMapper pageMapperMock

	private ConflictRepository conflictRepositoryMock

	private ConflictRestQuery conflictRestQuery

	void setup() {
		conflictBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		conflictRepositoryMock = Mock()
		conflictRestQuery = new ConflictRestQuery(conflictBaseRestMapperMock, pageMapperMock, conflictRepositoryMock)
	}

	void "maps ConflictRestBeanParams to ConflictRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		ConflictRestBeanParams conflictRestBeanParams = Mock()
		ConflictRequestDTO conflictRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = conflictRestQuery.query(conflictRestBeanParams)

		then:
		1 * conflictBaseRestMapperMock.mapBase(conflictRestBeanParams) >> conflictRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(conflictRestBeanParams) >> pageRequest
		1 * conflictRepositoryMock.findMatching(conflictRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
