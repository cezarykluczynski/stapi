package com.cezarykluczynski.stapi.server.character.query

import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class CharacterRestQueryTest extends Specification {

	private CharacterBaseRestMapper characterBaseRestMapperMock

	private PageMapper pageMapperMock

	private CharacterRepository characterRepositoryMock

	private CharacterRestQuery characterRestQuery

	void setup() {
		characterBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		characterRepositoryMock = Mock()
		characterRestQuery = new CharacterRestQuery(characterBaseRestMapperMock, pageMapperMock, characterRepositoryMock)
	}

	void "maps CharacterRestBeanParams to CharacterRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		CharacterRestBeanParams characterRestBeanParams = Mock()
		CharacterRequestDTO characterRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = characterRestQuery.query(characterRestBeanParams)

		then:
		1 * characterBaseRestMapperMock.mapBase(characterRestBeanParams) >> characterRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(characterRestBeanParams) >> pageRequest
		1 * characterRepositoryMock.findMatching(characterRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
