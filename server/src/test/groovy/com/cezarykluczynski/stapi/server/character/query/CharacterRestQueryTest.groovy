package com.cezarykluczynski.stapi.server.character.query

import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams
import com.cezarykluczynski.stapi.server.character.mapper.CharacterRestMapper
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class CharacterRestQueryTest extends Specification {

	private CharacterRestMapper characterRestMapperMock

	private PageMapper pageMapperMock

	private CharacterRepository characterRepositoryMock

	private CharacterRestQuery characterRestQuery

	void setup() {
		characterRestMapperMock = Mock(CharacterRestMapper)
		pageMapperMock = Mock(PageMapper)
		characterRepositoryMock = Mock(CharacterRepository)
		characterRestQuery = new CharacterRestQuery(characterRestMapperMock, pageMapperMock,
				characterRepositoryMock)
	}

	void "maps CharacterRestBeanParams to CharacterRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock(PageRequest)
		CharacterRestBeanParams characterRestBeanParams = Mock(CharacterRestBeanParams) {

		}
		CharacterRequestDTO characterRequestDTO = Mock(CharacterRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = characterRestQuery.query(characterRestBeanParams)

		then:
		1 * characterRestMapperMock.mapBase(characterRestBeanParams) >> characterRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(characterRestBeanParams) >> pageRequest
		1 * characterRepositoryMock.findMatching(characterRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
