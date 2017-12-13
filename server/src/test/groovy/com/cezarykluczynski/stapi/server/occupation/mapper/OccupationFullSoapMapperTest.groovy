package com.cezarykluczynski.stapi.server.occupation.mapper

import com.cezarykluczynski.stapi.client.v1.soap.OccupationFull
import com.cezarykluczynski.stapi.client.v1.soap.OccupationFullRequest
import com.cezarykluczynski.stapi.model.occupation.dto.OccupationRequestDTO
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import org.mapstruct.factory.Mappers

class OccupationFullSoapMapperTest extends AbstractOccupationMapperTest {

	private OccupationFullSoapMapper occupationFullSoapMapper

	void setup() {
		occupationFullSoapMapper = Mappers.getMapper(OccupationFullSoapMapper)
	}

	void "maps SOAP OccupationFullRequest to OccupationBaseRequestDTO"() {
		given:
		OccupationFullRequest occupationFullRequest = new OccupationFullRequest(uid: UID)

		when:
		OccupationRequestDTO occupationRequestDTO = occupationFullSoapMapper.mapFull occupationFullRequest

		then:
		occupationRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Occupation occupation = createOccupation()

		when:
		OccupationFull occupationFull = occupationFullSoapMapper.mapFull(occupation)

		then:
		occupationFull.uid == UID
		occupationFull.name == NAME
		occupationFull.legalOccupation == LEGAL_OCCUPATION
		occupationFull.medicalOccupation == MEDICAL_OCCUPATION
		occupationFull.scientificOccupation == SCIENTIFIC_OCCUPATION
		occupationFull.characters.size() == occupation.characters.size()
	}

}
