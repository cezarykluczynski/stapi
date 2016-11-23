package com.cezarykluczynski.stapi.server.character.query

import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams
import com.cezarykluczynski.stapi.server.character.mapper.CharacterRequestMapper
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class CharacterRestQueryTest extends Specification {

	private CharacterRequestMapper characterRequestMapperMock

	private PageMapper pageMapperMock

	private CharacterRepository characterRepositoryMock

	private CharacterRestQuery characterRestQuery

	def setup() {
		characterRequestMapperMock = Mock(CharacterRequestMapper)
		pageMapperMock = Mock(PageMapper)
		characterRepositoryMock = Mock(CharacterRepository)
		characterRestQuery = new CharacterRestQuery(characterRequestMapperMock, pageMapperMock,
				characterRepositoryMock)
	}

	def "maps CharacterRestBeanParams to CharacterRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock(PageRequest)
		CharacterRestBeanParams characterRestBeanParams = Mock(CharacterRestBeanParams) {

		}
		CharacterRequestDTO characterRequestDTO = Mock(CharacterRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = characterRestQuery.query(characterRestBeanParams)

		then:
		1 * characterRequestMapperMock.map(characterRestBeanParams) >> characterRequestDTO
		1 * pageMapperMock.fromPageAwareBeanParamsToPageRequest(characterRestBeanParams) >> pageRequest
		1 * characterRepositoryMock.findMatching(characterRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
