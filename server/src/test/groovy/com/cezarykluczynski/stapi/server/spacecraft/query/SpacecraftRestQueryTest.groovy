package com.cezarykluczynski.stapi.server.spacecraft.query

import com.cezarykluczynski.stapi.model.spacecraft.dto.SpacecraftRequestDTO
import com.cezarykluczynski.stapi.model.spacecraft.repository.SpacecraftRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.spacecraft.dto.SpacecraftRestBeanParams
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class SpacecraftRestQueryTest extends Specification {

	private SpacecraftBaseRestMapper spacecraftBaseRestMapperMock

	private PageMapper pageMapperMock

	private SpacecraftRepository spacecraftRepositoryMock

	private SpacecraftRestQuery spacecraftRestQuery

	void setup() {
		spacecraftBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		spacecraftRepositoryMock = Mock()
		spacecraftRestQuery = new SpacecraftRestQuery(spacecraftBaseRestMapperMock, pageMapperMock, spacecraftRepositoryMock)
	}

	void "maps SpacecraftRestBeanParams to SpacecraftRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		SpacecraftRestBeanParams spacecraftRestBeanParams = Mock()
		SpacecraftRequestDTO spacecraftRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = spacecraftRestQuery.query(spacecraftRestBeanParams)

		then:
		1 * spacecraftBaseRestMapperMock.mapBase(spacecraftRestBeanParams) >> spacecraftRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(spacecraftRestBeanParams) >> pageRequest
		1 * spacecraftRepositoryMock.findMatching(spacecraftRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
