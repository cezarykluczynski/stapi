package com.cezarykluczynski.stapi.server.character.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterResponse
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams
import com.cezarykluczynski.stapi.server.character.reader.CharacterRestReader
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest

class CharacterRestEndpointTest extends AbstractRestEndpointTest {

	private CharacterRestReader characterRestReaderMock

	private CharacterRestEndpoint characterRestEndpoint

	def setup() {
		characterRestReaderMock = Mock(CharacterRestReader)
		characterRestEndpoint = new CharacterRestEndpoint(characterRestReaderMock)
	}

	def "passes get call to CharacterRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock(PageSortBeanParams) {
			getPageNumber() >> PAGE_NUMBER
			getPageSize() >> PAGE_SIZE
		}
		CharacterResponse characterResponse = Mock(CharacterResponse)

		when:
		CharacterResponse characterResponseOutput = characterRestEndpoint.getCharacters(pageAwareBeanParams)

		then:
		1 * characterRestReaderMock.read(_ as CharacterRestBeanParams) >> { CharacterRestBeanParams characterRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			return characterResponse
		}
		characterResponseOutput == characterResponse
	}

	def "passes post call to CharacterRestReader"() {
		given:
		CharacterRestBeanParams characterRestBeanParams = new CharacterRestBeanParams()
		CharacterResponse characterResponse = Mock(CharacterResponse)

		when:
		CharacterResponse characterResponseOutput = characterRestEndpoint.searchCharacters(characterRestBeanParams)

		then:
		1 * characterRestReaderMock.read(characterRestBeanParams as CharacterRestBeanParams) >> { CharacterRestBeanParams params ->
			return characterResponse
		}
		characterResponseOutput == characterResponse
	}

}
