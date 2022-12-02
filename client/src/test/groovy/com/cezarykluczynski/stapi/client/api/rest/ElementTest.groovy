package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.ElementApi
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementV2FullResponse
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
		1 * elementApiMock.v1RestElementGet(UID, API_KEY) >> elementFullResponse
		0 * _
		elementFullResponse == elementFullResponseOutput
	}

	void "gets single entity (V2)"() {
		given:
		ElementV2FullResponse elementV2FullResponse = Mock()

		when:
		ElementV2FullResponse elementFullResponseOutput = element.getV2(UID)

		then:
		1 * elementApiMock.v2RestElementGet(UID, API_KEY) >> elementV2FullResponse
		0 * _
		elementV2FullResponse == elementFullResponseOutput
	}

	void "searches entities"() {
		given:
		ElementBaseResponse elementBaseResponse = Mock()

		when:
		ElementBaseResponse elementBaseResponseOutput = element.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, SYMBOL, TRANSURANIC, GAMMA_SERIES,
				HYPERSONIC_SERIES, MEGA_SERIES, OMEGA_SERIES, TRANSONIC_SERIES, WORLD_SERIES)

		then:
		1 * elementApiMock.v1RestElementSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, SYMBOL, TRANSURANIC, GAMMA_SERIES,
				HYPERSONIC_SERIES, MEGA_SERIES, OMEGA_SERIES, TRANSONIC_SERIES, WORLD_SERIES) >> elementBaseResponse
		0 * _
		elementBaseResponse == elementBaseResponseOutput
	}

	void "searches entities (V2)"() {
		given:
		ElementV2BaseResponse elementBaseResponse = Mock()

		when:
		ElementV2BaseResponse elementBaseResponseOutput = element.searchV2(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, SYMBOL, TRANSURANIC, GAMMA_SERIES,
				HYPERSONIC_SERIES, MEGA_SERIES, OMEGA_SERIES, TRANSONIC_SERIES, WORLD_SERIES)

		then:
		1 * elementApiMock.v2RestElementSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, SYMBOL, TRANSURANIC, GAMMA_SERIES,
				HYPERSONIC_SERIES, MEGA_SERIES, OMEGA_SERIES, TRANSONIC_SERIES, WORLD_SERIES) >> elementBaseResponse
		0 * _
		elementBaseResponse == elementBaseResponseOutput
	}

}
