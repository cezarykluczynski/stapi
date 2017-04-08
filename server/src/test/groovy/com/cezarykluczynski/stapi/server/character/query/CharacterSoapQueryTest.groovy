package com.cezarykluczynski.stapi.server.character.query

import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseSoapMapper
import com.cezarykluczynski.stapi.server.character.mapper.CharacterFullSoapMapper
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class CharacterSoapQueryTest extends Specification {

	private CharacterBaseSoapMapper characterBaseSoapMapperMock

	private CharacterFullSoapMapper characterFullSoapMapperMock

	private PageMapper pageMapperMock

	private CharacterRepository characterRepositoryMock

	private CharacterSoapQuery characterSoapQuery

	void setup() {
		characterBaseSoapMapperMock = Mock()
		characterFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		characterRepositoryMock = Mock()
		characterSoapQuery = new CharacterSoapQuery(characterBaseSoapMapperMock, characterFullSoapMapperMock, pageMapperMock, characterRepositoryMock)
	}

	void "maps CharacterBaseRequest to CharacterRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		CharacterBaseRequest characterRequest = Mock()
		characterRequest.page >> requestPage
		CharacterRequestDTO characterRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = characterSoapQuery.query(characterRequest)

		then:
		1 * characterBaseSoapMapperMock.mapBase(characterRequest) >> characterRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * characterRepositoryMock.findMatching(characterRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps CharacterFullRequest to CharacterRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		CharacterFullRequest characterRequest = Mock()
		CharacterRequestDTO characterRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = characterSoapQuery.query(characterRequest)

		then:
		1 * characterFullSoapMapperMock.mapFull(characterRequest) >> characterRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * characterRepositoryMock.findMatching(characterRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
