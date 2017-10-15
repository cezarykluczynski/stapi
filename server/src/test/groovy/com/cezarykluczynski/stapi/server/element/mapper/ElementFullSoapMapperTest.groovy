package com.cezarykluczynski.stapi.server.element.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ElementFull
import com.cezarykluczynski.stapi.client.v1.soap.ElementFullRequest
import com.cezarykluczynski.stapi.model.element.dto.ElementRequestDTO
import com.cezarykluczynski.stapi.model.element.entity.Element
import org.mapstruct.factory.Mappers

class ElementFullSoapMapperTest extends AbstractElementMapperTest {

	private ElementFullSoapMapper elementFullSoapMapper

	void setup() {
		elementFullSoapMapper = Mappers.getMapper(ElementFullSoapMapper)
	}

	void "maps SOAP ElementFullRequest to ElementBaseRequestDTO"() {
		given:
		ElementFullRequest elementFullRequest = new ElementFullRequest(uid: UID)

		when:
		ElementRequestDTO elementRequestDTO = elementFullSoapMapper.mapFull elementFullRequest

		then:
		elementRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Element element = createElement()

		when:
		ElementFull elementFull = elementFullSoapMapper.mapFull(element)

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
