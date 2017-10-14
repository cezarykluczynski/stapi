package com.cezarykluczynski.stapi.server.animal.query

import com.cezarykluczynski.stapi.model.animal.dto.AnimalRequestDTO
import com.cezarykluczynski.stapi.model.animal.repository.AnimalRepository
import com.cezarykluczynski.stapi.server.animal.dto.AnimalRestBeanParams
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalBaseRestMapper
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class AnimalRestQueryTest extends Specification {

	private AnimalBaseRestMapper animalRestMapperMock

	private PageMapper pageMapperMock

	private AnimalRepository animalRepositoryMock

	private AnimalRestQuery animalRestQuery

	void setup() {
		animalRestMapperMock = Mock()
		pageMapperMock = Mock()
		animalRepositoryMock = Mock()
		animalRestQuery = new AnimalRestQuery(animalRestMapperMock, pageMapperMock, animalRepositoryMock)
	}

	void "maps AnimalRestBeanParams to AnimalRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		AnimalRestBeanParams animalRestBeanParams = Mock()
		AnimalRequestDTO animalRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = animalRestQuery.query(animalRestBeanParams)

		then:
		1 * animalRestMapperMock.mapBase(animalRestBeanParams) >> animalRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(animalRestBeanParams) >> pageRequest
		1 * animalRepositoryMock.findMatching(animalRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
