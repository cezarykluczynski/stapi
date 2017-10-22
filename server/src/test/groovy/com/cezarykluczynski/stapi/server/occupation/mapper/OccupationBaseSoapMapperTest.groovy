package com.cezarykluczynski.stapi.server.occupation.mapper

import com.cezarykluczynski.stapi.client.v1.soap.OccupationBase
import com.cezarykluczynski.stapi.client.v1.soap.OccupationBaseRequest
import com.cezarykluczynski.stapi.model.occupation.dto.OccupationRequestDTO
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class OccupationBaseSoapMapperTest extends AbstractOccupationMapperTest {

	private OccupationBaseSoapMapper occupationBaseSoapMapper

	void setup() {
		occupationBaseSoapMapper = Mappers.getMapper(OccupationBaseSoapMapper)
	}

	void "maps SOAP OccupationBaseRequest to OccupationRequestDTO"() {
		given:
		OccupationBaseRequest occupationBaseRequest = new OccupationBaseRequest(
				name: NAME,
				legalOccupation: LEGAL_OCCUPATION,
				medicalOccupation: MEDICAL_OCCUPATION,
				scientificOccupation: SCIENTIFIC_OCCUPATION)

		when:
		OccupationRequestDTO occupationRequestDTO = occupationBaseSoapMapper.mapBase occupationBaseRequest

		then:
		occupationRequestDTO.name == NAME
		occupationRequestDTO.legalOccupation == LEGAL_OCCUPATION
		occupationRequestDTO.medicalOccupation == MEDICAL_OCCUPATION
		occupationRequestDTO.scientificOccupation == SCIENTIFIC_OCCUPATION
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		Occupation occupation = createOccupation()

		when:
		OccupationBase occupationBase = occupationBaseSoapMapper.mapBase(Lists.newArrayList(occupation))[0]

		then:
		occupationBase.uid == UID
		occupationBase.name == NAME
		occupationBase.legalOccupation == LEGAL_OCCUPATION
		occupationBase.medicalOccupation == MEDICAL_OCCUPATION
		occupationBase.scientificOccupation == SCIENTIFIC_OCCUPATION
	}

}
