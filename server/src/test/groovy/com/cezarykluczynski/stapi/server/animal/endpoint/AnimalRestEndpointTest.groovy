package com.cezarykluczynski.stapi.server.animal.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalFullResponse
import com.cezarykluczynski.stapi.server.animal.dto.AnimalRestBeanParams
import com.cezarykluczynski.stapi.server.animal.reader.AnimalRestReader
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest

class AnimalRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private AnimalRestReader animalRestReaderMock

	private AnimalRestEndpoint animalRestEndpoint

	void setup() {
		animalRestReaderMock = Mock()
		animalRestEndpoint = new AnimalRestEndpoint(animalRestReaderMock)
	}

	void "passes get call to AnimalRestReader"() {
		given:
		AnimalFullResponse animalFullResponse = Mock()

		when:
		AnimalFullResponse animalFullResponseOutput = animalRestEndpoint.getAnimal(UID)

		then:
		1 * animalRestReaderMock.readFull(UID) >> animalFullResponse
		animalFullResponseOutput == animalFullResponse
	}

	void "passes search get call to AnimalRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		AnimalBaseResponse animalResponse = Mock()

		when:
		AnimalBaseResponse animalResponseOutput = animalRestEndpoint.searchAnimals(pageAwareBeanParams)

		then:
		1 * animalRestReaderMock.readBase(_ as AnimalRestBeanParams) >> { AnimalRestBeanParams animalRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			animalResponse
		}
		animalResponseOutput == animalResponse
	}

	void "passes search post call to AnimalRestReader"() {
		given:
		AnimalRestBeanParams animalRestBeanParams = new AnimalRestBeanParams(name: NAME)
		AnimalBaseResponse animalResponse = Mock()

		when:
		AnimalBaseResponse animalResponseOutput = animalRestEndpoint.searchAnimals(animalRestBeanParams)

		then:
		1 * animalRestReaderMock.readBase(animalRestBeanParams as AnimalRestBeanParams) >> { AnimalRestBeanParams params ->
			assert params.name == NAME
			animalResponse
		}
		animalResponseOutput == animalResponse
	}

}
