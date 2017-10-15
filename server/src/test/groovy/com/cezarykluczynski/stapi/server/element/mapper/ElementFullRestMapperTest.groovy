package com.cezarykluczynski.stapi.server.element.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ElementFull
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
		elementFull.transuranium == TRANSURANIUM
		elementFull.gammaSeries == GAMMA_SERIES
		elementFull.hypersonicSeries == HYPERSONIC_SERIES
		elementFull.megaSeries == MEGA_SERIES
		elementFull.omegaSeries == OMEGA_SERIES
		elementFull.transonicSeries == TRANSONIC_SERIES
		elementFull.worldSeries == WORLD_SERIES
	}

}
