package com.cezarykluczynski.stapi.server.literature.query

import com.cezarykluczynski.stapi.model.literature.dto.LiteratureRequestDTO
import com.cezarykluczynski.stapi.model.literature.repository.LiteratureRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.literature.dto.LiteratureRestBeanParams
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class LiteratureRestQueryTest extends Specification {

	private LiteratureBaseRestMapper literatureRestMapperMock

	private PageMapper pageMapperMock

	private LiteratureRepository literatureRepositoryMock

	private LiteratureRestQuery literatureRestQuery

	void setup() {
		literatureRestMapperMock = Mock()
		pageMapperMock = Mock()
		literatureRepositoryMock = Mock()
		literatureRestQuery = new LiteratureRestQuery(literatureRestMapperMock, pageMapperMock, literatureRepositoryMock)
	}

	void "maps LiteratureRestBeanParams to LiteratureRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		LiteratureRestBeanParams literatureRestBeanParams = Mock()
		LiteratureRequestDTO literatureRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = literatureRestQuery.query(literatureRestBeanParams)

		then:
		1 * literatureRestMapperMock.mapBase(literatureRestBeanParams) >> literatureRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(literatureRestBeanParams) >> pageRequest
		1 * literatureRepositoryMock.findMatching(literatureRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
