package com.cezarykluczynski.stapi.server.character.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterFullResponse
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams
import com.cezarykluczynski.stapi.server.character.reader.CharacterRestReader
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest

class CharacterRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private CharacterRestReader characterRestReaderMock

	private CharacterRestEndpoint characterRestEndpoint

	void setup() {
		characterRestReaderMock = Mock()
		characterRestEndpoint = new CharacterRestEndpoint(characterRestReaderMock)
	}

	void "passes get call to CharacterRestReader"() {
		given:
		CharacterFullResponse characterFullResponse = Mock()

		when:
		CharacterFullResponse characterFullResponseOutput = characterRestEndpoint.getCharacter(UID)

		then:
		1 * characterRestReaderMock.readFull(UID) >> characterFullResponse
		characterFullResponseOutput == characterFullResponse
	}

	void "passes search get call to CharacterRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		CharacterBaseResponse characterResponse = Mock()

		when:
		CharacterBaseResponse characterResponseOutput = characterRestEndpoint.searchCharacter(pageAwareBeanParams)

		then:
		1 * characterRestReaderMock.readBase(_ as CharacterRestBeanParams) >> { CharacterRestBeanParams characterRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			characterResponse
		}
		characterResponseOutput == characterResponse
	}

	void "passes search post call to CharacterRestReader"() {
		given:
		CharacterRestBeanParams characterRestBeanParams = new CharacterRestBeanParams(name: NAME)
		CharacterBaseResponse characterResponse = Mock()

		when:
		CharacterBaseResponse characterResponseOutput = characterRestEndpoint.searchCharacter(characterRestBeanParams)

		then:
		1 * characterRestReaderMock.readBase(characterRestBeanParams as CharacterRestBeanParams) >> { CharacterRestBeanParams params ->
			assert params.name == NAME
			characterResponse
		}
		characterResponseOutput == characterResponse
	}

}
