package com.cezarykluczynski.stapi.server.character.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.model.character.entity.Character as DBCharacter
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams
import com.cezarykluczynski.stapi.server.character.mapper.CharacterRestMapper
import com.cezarykluczynski.stapi.server.character.query.CharacterRestQuery
import com.cezarykluczynski.stapi.client.v1.rest.model.Character as RESTCharacter
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

	def setup() {
		characterRestQueryBuilderMock = Mock(CharacterRestQuery)
		characterRestMapperMock = Mock(CharacterRestMapper)
		pageMapperMock = Mock(PageMapper)
		characterRestReader = new CharacterRestReader(characterRestQueryBuilderMock, characterRestMapperMock, pageMapperMock)
	}

	def "gets database entities and puts them into CharacterResponse"() {
		given:
		List<DBCharacter> dbCharacterList = Lists.newArrayList()
		Page<DBCharacter> dbCharacterPage = Mock(Page) {
			getContent() >> dbCharacterList
		}
		List<RESTCharacter> soapCharacterList = Lists.newArrayList(new RESTCharacter(guid: GUID))
		CharacterRestBeanParams seriesRestBeanParams = Mock(CharacterRestBeanParams)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		CharacterResponse characterResponse = characterRestReader.read(seriesRestBeanParams)

		then:
		1 * characterRestQueryBuilderMock.query(seriesRestBeanParams) >> dbCharacterPage
		1 * pageMapperMock.fromPageToRestResponsePage(dbCharacterPage) >> responsePage
		1 * characterRestMapperMock.map(dbCharacterList) >> soapCharacterList
		characterResponse.characters[0].guid == GUID
		characterResponse.page == responsePage
	}

}
