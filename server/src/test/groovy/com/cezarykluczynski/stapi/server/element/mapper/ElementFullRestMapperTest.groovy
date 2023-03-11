package com.cezarykluczynski.stapi.server.element.mapper

import com.cezarykluczynski.stapi.client.rest.model.ElementFull
import com.cezarykluczynski.stapi.client.rest.model.ElementV2Full
import com.cezarykluczynski.stapi.model.element.entity.Element
import org.mapstruct.factory.Mappers

class ElementFullRestMapperTest extends AbstractElementMapperTest {

	private ElementFullRestMapper elementFullRestMapper

	void setup() {
		elementFullRestMapper = Mappers.getMapper(ElementFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Element dBElement = createElement()

		when:
		ElementFull elementFull = elementFullRestMapper.mapFull(dBElement)

		then:
		elementFull.uid == UID
		elementFull.name == NAME
		elementFull.symbol == SYMBOL
		elementFull.atomicNumber == ATOMIC_NUMBER
		elementFull.atomicWeight == ATOMIC_WEIGHT
		elementFull.transuranium == TRANSURANIC
		elementFull.gammaSeries == GAMMA_SERIES
		elementFull.hypersonicSeries == HYPERSONIC_SERIES
		elementFull.megaSeries == MEGA_SERIES
		elementFull.omegaSeries == OMEGA_SERIES
		elementFull.transonicSeries == TRANSONIC_SERIES
		elementFull.worldSeries == WORLD_SERIES
	}

	void "maps DB entity to full REST V2 entity"() {
		given:
		Element dBElement = createElement()

		when:
		ElementV2Full elementV2Full = elementFullRestMapper.mapV2Full(dBElement)

		then:
		elementV2Full.uid == UID
		elementV2Full.name == NAME
		elementV2Full.symbol == SYMBOL
		elementV2Full.atomicNumber == ATOMIC_NUMBER
		elementV2Full.atomicWeight == ATOMIC_WEIGHT
		elementV2Full.transuranic == TRANSURANIC
		elementV2Full.gammaSeries == GAMMA_SERIES
		elementV2Full.hypersonicSeries == HYPERSONIC_SERIES
		elementV2Full.megaSeries == MEGA_SERIES
		elementV2Full.omegaSeries == OMEGA_SERIES
		elementV2Full.transonicSeries == TRANSONIC_SERIES
		elementV2Full.worldSeries == WORLD_SERIES
	}

}
