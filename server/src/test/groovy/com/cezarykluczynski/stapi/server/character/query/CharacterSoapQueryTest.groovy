package com.cezarykluczynski.stapi.server.character.query

import com.cezarykluczynski.stapi.client.v1.soap.CharacterRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.cezarykluczynski.stapi.server.character.mapper.CharacterRequestMapper
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class CharacterSoapQueryTest extends Specification {

	private CharacterRequestMapper characterRequestMapperMock

	private PageMapper pageMapperMock

	private CharacterRepository characterRepositoryMock

	private CharacterSoapQuery characterSoapQuery

	def setup() {
		characterRequestMapperMock = Mock(CharacterRequestMapper)
		pageMapperMock = Mock(PageMapper)
		characterRepositoryMock = Mock(CharacterRepository)
		characterSoapQuery = new CharacterSoapQuery(characterRequestMapperMock, pageMapperMock,
				characterRepositoryMock)
	}

	def "maps CharacterRequest to CharacterRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)
		CharacterRequest characterRequest = Mock(CharacterRequest) {
			getPage() >> requestPage
		}
		CharacterRequestDTO characterRequestDTO = Mock(CharacterRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = characterSoapQuery.query(characterRequest)

		then:
		1 * characterRequestMapperMock.map(characterRequest) >> characterRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * characterRepositoryMock.findMatching(characterRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
