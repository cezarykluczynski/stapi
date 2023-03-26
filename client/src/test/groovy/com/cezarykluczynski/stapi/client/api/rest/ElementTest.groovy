package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.ElementApi
import com.cezarykluczynski.stapi.client.rest.model.ElementV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.ElementV2FullResponse
import com.cezarykluczynski.stapi.client.rest.model.ElementV2SearchCriteria
import com.cezarykluczynski.stapi.util.AbstractElementTest

class ElementTest extends AbstractElementTest {

	private ElementApi elementApiMock

	private Element element

	void setup() {
		elementApiMock = Mock()
		element = new Element(elementApiMock)
	}

	void "gets single entity (V2)"() {
		given:
		ElementV2FullResponse elementV2FullResponse = Mock()

		when:
		ElementV2FullResponse elementFullResponseOutput = element.getV2(UID)

		then:
		1 * elementApiMock.v2GetElement(UID) >> elementV2FullResponse
		0 * _
		elementV2FullResponse == elementFullResponseOutput
	}

	void "searches entities with criteria (V2)"() {
		given:
		ElementV2BaseResponse elementBaseResponse = Mock()
		ElementV2SearchCriteria elementV2SearchCriteria = new ElementV2SearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				symbol: SYMBOL,
				transuranic: TRANSURANIC,
				gammaSeries: GAMMA_SERIES,
				hypersonicSeries: HYPERSONIC_SERIES,
				megaSeries: MEGA_SERIES,
				omegaSeries: OMEGA_SERIES,
				transonicSeries: TRANSONIC_SERIES,
				worldSeries: WORLD_SERIES)
		elementV2SearchCriteria.sort = SORT

		when:
		ElementV2BaseResponse elementBaseResponseOutput = element.searchV2(elementV2SearchCriteria)

		then:
		1 * elementApiMock.v2SearchElements(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, SYMBOL, TRANSURANIC, GAMMA_SERIES,
				HYPERSONIC_SERIES, MEGA_SERIES, OMEGA_SERIES, TRANSONIC_SERIES, WORLD_SERIES) >> elementBaseResponse
		0 * _
		elementBaseResponse == elementBaseResponseOutput
	}

}
