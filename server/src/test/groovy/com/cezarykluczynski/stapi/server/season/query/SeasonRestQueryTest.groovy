package com.cezarykluczynski.stapi.server.season.query

import com.cezarykluczynski.stapi.model.season.dto.SeasonRequestDTO
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.season.dto.SeasonRestBeanParams
import com.cezarykluczynski.stapi.server.season.mapper.SeasonBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class SeasonRestQueryTest extends Specification {

	private SeasonBaseRestMapper seasonRestMapperMock

	private PageMapper pageMapperMock

	private SeasonRepository seasonRepositoryMock

	private SeasonRestQuery seasonRestQuery

	void setup() {
		seasonRestMapperMock = Mock()
		pageMapperMock = Mock()
		seasonRepositoryMock = Mock()
		seasonRestQuery = new SeasonRestQuery(seasonRestMapperMock, pageMapperMock, seasonRepositoryMock)
	}

	void "maps SeasonRestBeanParams to SeasonRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		SeasonRestBeanParams seasonRestBeanParams = Mock()
		SeasonRequestDTO seasonRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = seasonRestQuery.query(seasonRestBeanParams)

		then:
		1 * seasonRestMapperMock.mapBase(seasonRestBeanParams) >> seasonRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(seasonRestBeanParams) >> pageRequest
		1 * seasonRepositoryMock.findMatching(seasonRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
