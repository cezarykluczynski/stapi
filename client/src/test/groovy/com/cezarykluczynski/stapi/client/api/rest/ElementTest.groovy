package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.ElementApi
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementFullResponse
import com.cezarykluczynski.stapi.util.AbstractElementTest

class ElementTest extends AbstractElementTest {

	private ElementApi elementApiMock

	private Element element

	void setup() {
		elementApiMock = Mock()
		element = new Element(elementApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		ElementFullResponse elementFullResponse = Mock()

		when:
		ElementFullResponse elementFullResponseOutput = element.get(UID)

		then:
		1 * elementApiMock.elementGet(UID, API_KEY) >> elementFullResponse
		0 * _
		elementFullResponse == elementFullResponseOutput
	}

	void "searches entities"() {
		given:
		ElementBaseResponse elementBaseResponse = Mock()

		when:
		ElementBaseResponse elementBaseResponseOutput = element.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, SYMBOL, TRANSURANIUM, GAMMA_SERIES,
				HYPERSONIC_SERIES, MEGA_SERIES, OMEGA_SERIES, TRANSONIC_SERIES, WORLD_SERIES)

		then:
		1 * elementApiMock.elementSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, SYMBOL, TRANSURANIUM, GAMMA_SERIES,
				HYPERSONIC_SERIES, MEGA_SERIES, OMEGA_SERIES, TRANSONIC_SERIES, WORLD_SERIES) >> elementBaseResponse
		0 * _
		elementBaseResponse == elementBaseResponseOutput
	}

}
