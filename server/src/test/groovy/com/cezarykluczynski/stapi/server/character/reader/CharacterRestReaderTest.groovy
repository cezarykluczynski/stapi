package com.cezarykluczynski.stapi.server.character.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterBase
import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterFull
import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper
import com.cezarykluczynski.stapi.server.character.mapper.CharacterFullRestMapper
import com.cezarykluczynski.stapi.server.character.query.CharacterRestQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class CharacterRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private CharacterRestQuery characterRestQueryBuilderMock

	private CharacterBaseRestMapper characterBaseRestMapperMock

	private CharacterFullRestMapper characterFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private CharacterRestReader characterRestReader

	void setup() {
		characterRestQueryBuilderMock = Mock()
		characterBaseRestMapperMock = Mock()
		characterFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		characterRestReader = new CharacterRestReader(characterRestQueryBuilderMock, characterBaseRestMapperMock, characterFullRestMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		CharacterBase characterBase = Mock()
		Character character = Mock()
		CharacterRestBeanParams characterRestBeanParams = Mock()
		List<CharacterBase> restCharacterList = Lists.newArrayList(characterBase)
		List<Character> characterList = Lists.newArrayList(character)
		Page<Character> characterPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		CharacterBaseResponse characterResponseOutput = characterRestReader.readBase(characterRestBeanParams)

		then:
		1 * characterRestQueryBuilderMock.query(characterRestBeanParams) >> characterPage
		1 * pageMapperMock.fromPageToRestResponsePage(characterPage) >> responsePage
		1 * characterRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * characterPage.content >> characterList
		1 * characterBaseRestMapperMock.mapBase(characterList) >> restCharacterList
		0 * _
		characterResponseOutput.characters == restCharacterList
		characterResponseOutput.page == responsePage
		characterResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		CharacterFull characterFull = Mock()
		Character character = Mock()
		List<Character> characterList = Lists.newArrayList(character)
		Page<Character> characterPage = Mock()

		when:
		CharacterFullResponse characterResponseOutput = characterRestReader.readFull(UID)

		then:
		1 * characterRestQueryBuilderMock.query(_ as CharacterRestBeanParams) >> { CharacterRestBeanParams characterRestBeanParams ->
			assert characterRestBeanParams.uid == UID
			characterPage
		}
		1 * characterPage.content >> characterList
		1 * characterFullRestMapperMock.mapFull(character) >> characterFull
		0 * _
		characterResponseOutput.character == characterFull
	}

	void "requires UID in full request"() {
		when:
		characterRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
