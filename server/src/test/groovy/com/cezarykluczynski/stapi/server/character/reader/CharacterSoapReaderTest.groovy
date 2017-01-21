package com.cezarykluczynski.stapi.server.character.reader

import com.cezarykluczynski.stapi.client.v1.soap.Character as SOAPCharacter
import com.cezarykluczynski.stapi.client.v1.soap.CharacterRequest
import com.cezarykluczynski.stapi.client.v1.soap.CharacterResponse
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.model.character.entity.Character as DBCharacter
import com.cezarykluczynski.stapi.server.character.mapper.CharacterSoapMapper
import com.cezarykluczynski.stapi.server.character.query.CharacterSoapQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class CharacterSoapReaderTest extends Specification {

	private static final String GUID = 'GUID'

	private CharacterSoapQuery characterSoapQueryBuilderMock

	private CharacterSoapMapper characterSoapMapperMock

	private PageMapper pageMapperMock

	private CharacterSoapReader characterSoapReader

	void setup() {
		characterSoapQueryBuilderMock = Mock(CharacterSoapQuery)
		characterSoapMapperMock = Mock(CharacterSoapMapper)
		pageMapperMock = Mock(PageMapper)
		characterSoapReader = new CharacterSoapReader(characterSoapQueryBuilderMock, characterSoapMapperMock, pageMapperMock)
	}

	void "gets database entities and puts them into CharacterResponse"() {
		given:
		List<DBCharacter> dbCharacterList = Lists.newArrayList()
		Page<DBCharacter> dbCharacterPage = Mock(Page)
		dbCharacterPage.content >> dbCharacterList
		List<SOAPCharacter> soapCharacterList = Lists.newArrayList(new SOAPCharacter(guid: GUID))
		CharacterRequest characterRequest = Mock(CharacterRequest)
		ResponsePage responsePage = Mock(ResponsePage)

		when:
		CharacterResponse characterResponse = characterSoapReader.read(characterRequest)

		then:
		1 * characterSoapQueryBuilderMock.query(characterRequest) >> dbCharacterPage
		1 * pageMapperMock.fromPageToSoapResponsePage(dbCharacterPage) >> responsePage
		1 * characterSoapMapperMock.map(dbCharacterList) >> soapCharacterList
		characterResponse.characters[0].guid == GUID
		characterResponse.page == responsePage
	}

}
