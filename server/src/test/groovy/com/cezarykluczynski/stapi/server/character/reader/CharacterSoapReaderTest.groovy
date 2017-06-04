package com.cezarykluczynski.stapi.server.character.reader

import com.cezarykluczynski.stapi.client.v1.soap.CharacterBase
import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFull
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseSoapMapper
import com.cezarykluczynski.stapi.server.character.mapper.CharacterFullSoapMapper
import com.cezarykluczynski.stapi.server.character.query.CharacterSoapQuery
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class CharacterSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private CharacterSoapQuery characterSoapQueryBuilderMock

	private CharacterBaseSoapMapper characterBaseSoapMapperMock

	private CharacterFullSoapMapper characterFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private CharacterSoapReader characterSoapReader

	void setup() {
		characterSoapQueryBuilderMock = Mock()
		characterBaseSoapMapperMock = Mock()
		characterFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		characterSoapReader = new CharacterSoapReader(characterSoapQueryBuilderMock, characterBaseSoapMapperMock, characterFullSoapMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Character> characterList = Lists.newArrayList()
		Page<Character> characterPage = Mock()
		List<CharacterBase> soapCharacterList = Lists.newArrayList(new CharacterBase(uid: UID))
		CharacterBaseRequest characterBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		CharacterBaseResponse characterResponse = characterSoapReader.readBase(characterBaseRequest)

		then:
		1 * characterSoapQueryBuilderMock.query(characterBaseRequest) >> characterPage
		1 * characterPage.content >> characterList
		1 * pageMapperMock.fromPageToSoapResponsePage(characterPage) >> responsePage
		1 * characterBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * characterBaseSoapMapperMock.mapBase(characterList) >> soapCharacterList
		0 * _
		characterResponse.characters[0].uid == UID
		characterResponse.page == responsePage
		characterResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		CharacterFull characterFull = new CharacterFull(uid: UID)
		Character character = Mock()
		Page<Character> characterPage = Mock()
		CharacterFullRequest characterFullRequest = new CharacterFullRequest(uid: UID)

		when:
		CharacterFullResponse characterFullResponse = characterSoapReader.readFull(characterFullRequest)

		then:
		1 * characterSoapQueryBuilderMock.query(characterFullRequest) >> characterPage
		1 * characterPage.content >> Lists.newArrayList(character)
		1 * characterFullSoapMapperMock.mapFull(character) >> characterFull
		0 * _
		characterFullResponse.character.uid == UID
	}

	void "requires UID in full request"() {
		given:
		CharacterFullRequest characterFullRequest = Mock()

		when:
		characterSoapReader.readFull(characterFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
