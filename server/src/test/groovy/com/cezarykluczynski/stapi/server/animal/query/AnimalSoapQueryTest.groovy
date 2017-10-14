package com.cezarykluczynski.stapi.server.animal.query

import com.cezarykluczynski.stapi.client.v1.soap.AnimalBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.AnimalFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.animal.dto.AnimalRequestDTO
import com.cezarykluczynski.stapi.model.animal.repository.AnimalRepository
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalBaseSoapMapper
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalFullSoapMapper
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class AnimalSoapQueryTest extends Specification {

	private AnimalBaseSoapMapper animalBaseSoapMapperMock

	private AnimalFullSoapMapper animalFullSoapMapperMock

	private PageMapper pageMapperMock

	private AnimalRepository animalRepositoryMock

	private AnimalSoapQuery animalSoapQuery

	void setup() {
		animalBaseSoapMapperMock = Mock()
		animalFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		animalRepositoryMock = Mock()
		animalSoapQuery = new AnimalSoapQuery(animalBaseSoapMapperMock, animalFullSoapMapperMock, pageMapperMock, animalRepositoryMock)
	}

	void "maps AnimalBaseRequest to AnimalRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		AnimalBaseRequest animalRequest = Mock()
		animalRequest.page >> requestPage
		AnimalRequestDTO animalRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = animalSoapQuery.query(animalRequest)

		then:
		1 * animalBaseSoapMapperMock.mapBase(animalRequest) >> animalRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * animalRepositoryMock.findMatching(animalRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps AnimalFullRequest to AnimalRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		AnimalFullRequest animalRequest = Mock()
		AnimalRequestDTO animalRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = animalSoapQuery.query(animalRequest)

		then:
		1 * animalFullSoapMapperMock.mapFull(animalRequest) >> animalRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * animalRepositoryMock.findMatching(animalRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
