package com.cezarykluczynski.stapi.server.element.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ElementBase
import com.cezarykluczynski.stapi.client.v1.soap.ElementBaseRequest
import com.cezarykluczynski.stapi.model.element.dto.ElementRequestDTO
import com.cezarykluczynski.stapi.model.element.entity.Element
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class ElementBaseSoapMapperTest extends AbstractElementMapperTest {

	private ElementBaseSoapMapper elementBaseSoapMapper

	void setup() {
		elementBaseSoapMapper = Mappers.getMapper(ElementBaseSoapMapper)
	}

	void "maps SOAP ElementBaseRequest to ElementRequestDTO"() {
		given:
		ElementBaseRequest elementBaseRequest = new ElementBaseRequest(
				name: NAME,
				symbol: SYMBOL,
				transuranium: TRANSURANIUM,
				gammaSeries: GAMMA_SERIES,
				hypersonicSeries: HYPERSONIC_SERIES,
				megaSeries: MEGA_SERIES,
				omegaSeries: OMEGA_SERIES,
				transonicSeries: TRANSONIC_SERIES,
				worldSeries: WORLD_SERIES)

		when:
		ElementRequestDTO elementRequestDTO = elementBaseSoapMapper.mapBase elementBaseRequest

		then:
		elementRequestDTO.name == NAME
		elementRequestDTO.symbol == SYMBOL
		elementRequestDTO.transuranium == TRANSURANIUM
		elementRequestDTO.gammaSeries == GAMMA_SERIES
		elementRequestDTO.hypersonicSeries == HYPERSONIC_SERIES
		elementRequestDTO.megaSeries == MEGA_SERIES
		elementRequestDTO.omegaSeries == OMEGA_SERIES
		elementRequestDTO.transonicSeries == TRANSONIC_SERIES
		elementRequestDTO.worldSeries == WORLD_SERIES
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		Element element = createElement()

		when:
		ElementBase elementBase = elementBaseSoapMapper.mapBase(Lists.newArrayList(element))[0]

		then:
		elementBase.uid == UID
		elementBase.name == NAME
		elementBase.symbol == SYMBOL
		elementBase.atomicNumber == ATOMIC_NUMBER
		elementBase.atomicWeight == ATOMIC_WEIGHT
		elementBase.transuranium == TRANSURANIUM
		elementBase.gammaSeries == GAMMA_SERIES
		elementBase.hypersonicSeries == HYPERSONIC_SERIES
		elementBase.megaSeries == MEGA_SERIES
		elementBase.omegaSeries == OMEGA_SERIES
		elementBase.transonicSeries == TRANSONIC_SERIES
		elementBase.worldSeries == WORLD_SERIES
	}

}
