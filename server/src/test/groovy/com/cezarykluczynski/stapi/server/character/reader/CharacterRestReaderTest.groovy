package com.cezarykluczynski.stapi.server.character.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterBase
import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterFull
import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams
import com.cezarykluczynski.stapi.server.character.mapper.CharacterRestMapper
import com.cezarykluczynski.stapi.server.character.query.CharacterRestQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class CharacterRestReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private CharacterRestQuery characterRestQueryBuilderMock

	private CharacterRestMapper characterRestMapperMock

	private PageMapper pageMapperMock

	private CharacterRestReader characterRestReader

	void setup() {
		characterRestQueryBuilderMock = Mock(CharacterRestQuery)
		characterRestMapperMock = Mock(CharacterRestMapper)
		pageMapperMock = Mock(PageMapper)
		characterRestReader = new CharacterRestReader(characterRestQueryBuilderMock, characterRestMapperMock, pageMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		CharacterRestBeanParams characterRestBeanParams = Mock(CharacterRestBeanParams)
		List<CharacterBase> restCharacterList = Lists.newArrayList(Mock(CharacterBase))
		List<Character> characterList = Lists.newArrayList(Mock(Character))
		Page<Character> characterPage = Mock(Page)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		CharacterBaseResponse characterResponseOutput = characterRestReader.readBase(characterRestBeanParams)

		then:
		1 * characterRestQueryBuilderMock.query(characterRestBeanParams) >> characterPage
		1 * pageMapperMock.fromPageToRestResponsePage(characterPage) >> responsePage
		1 * characterPage.content >> characterList
		1 * characterRestMapperMock.mapBase(characterList) >> restCharacterList
		0 * _
		characterResponseOutput.characters == restCharacterList
		characterResponseOutput.page == responsePage
	}

	void "passed GUID to queryBuilder, then to mapper, and returns result"() {
		given:
		CharacterFull characterFull = Mock(CharacterFull)
		Character character = Mock(Character)
		List<Character> characterList = Lists.newArrayList(character)
		Page<Character> characterPage = Mock(Page)

		when:
		CharacterFullResponse characterResponseOutput = characterRestReader.readFull(GUID)

		then:
		1 * characterRestQueryBuilderMock.query(_ as CharacterRestBeanParams) >> { CharacterRestBeanParams characterRestBeanParams ->
			assert characterRestBeanParams.guid == GUID
			characterPage
		}
		1 * characterPage.content >> characterList
		1 * characterRestMapperMock.mapFull(character) >> characterFull
		0 * _
		characterResponseOutput.character == characterFull
	}

}
