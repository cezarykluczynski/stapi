package com.cezarykluczynski.stapi.server.character.query

import com.cezarykluczynski.stapi.client.v1.soap.CharacterRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.cezarykluczynski.stapi.server.character.mapper.CharacterSoapMapper
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class CharacterSoapQueryTest extends Specification {

	private CharacterSoapMapper characterSoapMapperMock

	private PageMapper pageMapperMock

	private CharacterRepository characterRepositoryMock

	private CharacterSoapQuery characterSoapQuery

	void setup() {
		characterSoapMapperMock = Mock(CharacterSoapMapper)
		pageMapperMock = Mock(PageMapper)
		characterRepositoryMock = Mock(CharacterRepository)
		characterSoapQuery = new CharacterSoapQuery(characterSoapMapperMock, pageMapperMock,
				characterRepositoryMock)
	}

	void "maps CharacterRequest to CharacterRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)
		CharacterRequest characterRequest = Mock(CharacterRequest)
		characterRequest.page >> requestPage
		CharacterRequestDTO characterRequestDTO = Mock(CharacterRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = characterSoapQuery.query(characterRequest)

		then:
		1 * characterSoapMapperMock.map(characterRequest) >> characterRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * characterRepositoryMock.findMatching(characterRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
