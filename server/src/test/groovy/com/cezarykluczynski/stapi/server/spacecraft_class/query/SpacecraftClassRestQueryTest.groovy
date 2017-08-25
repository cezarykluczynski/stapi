package com.cezarykluczynski.stapi.server.spacecraft_class.query

import com.cezarykluczynski.stapi.model.spacecraft_class.dto.SpacecraftClassRequestDTO
import com.cezarykluczynski.stapi.model.spacecraft_class.repository.SpacecraftClassRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.spacecraft_class.dto.SpacecraftClassRestBeanParams
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class SpacecraftClassRestQueryTest extends Specification {

	private SpacecraftClassBaseRestMapper spacecraftClassBaseRestMapperMock

	private PageMapper pageMapperMock

	private SpacecraftClassRepository spacecraftClassRepositoryMock

	private SpacecraftClassRestQuery spacecraftClassRestQuery

	void setup() {
		spacecraftClassBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		spacecraftClassRepositoryMock = Mock()
		spacecraftClassRestQuery = new SpacecraftClassRestQuery(spacecraftClassBaseRestMapperMock, pageMapperMock, spacecraftClassRepositoryMock)
	}

	void "maps SpacecraftClassRestBeanParams to SpacecraftClassRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		SpacecraftClassRestBeanParams spacecraftClassRestBeanParams = Mock()
		SpacecraftClassRequestDTO spacecraftClassRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = spacecraftClassRestQuery.query(spacecraftClassRestBeanParams)

		then:
		1 * spacecraftClassBaseRestMapperMock.mapBase(spacecraftClassRestBeanParams) >> spacecraftClassRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(spacecraftClassRestBeanParams) >> pageRequest
		1 * spacecraftClassRepositoryMock.findMatching(spacecraftClassRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
