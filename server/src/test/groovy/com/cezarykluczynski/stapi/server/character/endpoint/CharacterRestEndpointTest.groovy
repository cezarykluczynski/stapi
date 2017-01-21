package com.cezarykluczynski.stapi.server.character.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterResponse
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams
import com.cezarykluczynski.stapi.server.character.reader.CharacterRestReader
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest

class CharacterRestEndpointTest extends AbstractRestEndpointTest {

	private CharacterRestReader characterRestReaderMock

	private CharacterRestEndpoint characterRestEndpoint

	void setup() {
		characterRestReaderMock = Mock(CharacterRestReader)
		characterRestEndpoint = new CharacterRestEndpoint(characterRestReaderMock)
	}

	void "passes get call to CharacterRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock(PageSortBeanParams)
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		CharacterResponse characterResponse = Mock(CharacterResponse)

		when:
		CharacterResponse characterResponseOutput = characterRestEndpoint.getCharacters(pageAwareBeanParams)

		then:
		1 * characterRestReaderMock.read(_ as CharacterRestBeanParams) >> { CharacterRestBeanParams characterRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			characterResponse
		}
		characterResponseOutput == characterResponse
	}

	void "passes post call to CharacterRestReader"() {
		given:
		CharacterRestBeanParams characterRestBeanParams = new CharacterRestBeanParams()
		CharacterResponse characterResponse = Mock(CharacterResponse)

		when:
		CharacterResponse characterResponseOutput = characterRestEndpoint.searchCharacters(characterRestBeanParams)

		then:
		1 * characterRestReaderMock.read(characterRestBeanParams as CharacterRestBeanParams) >> { CharacterRestBeanParams params ->
			characterResponse
		}
		characterResponseOutput == characterResponse
	}

}
