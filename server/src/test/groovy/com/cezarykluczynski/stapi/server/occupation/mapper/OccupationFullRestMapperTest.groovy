package com.cezarykluczynski.stapi.server.occupation.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationFull
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import org.mapstruct.factory.Mappers

class OccupationFullRestMapperTest extends AbstractOccupationMapperTest {

	private OccupationFullRestMapper occupationFullRestMapper

	void setup() {
		occupationFullRestMapper = Mappers.getMapper(OccupationFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		Occupation occupation = createOccupation()

		when:
		OccupationFull occupationFull = occupationFullRestMapper.mapFull(occupation)

		then:
		occupationFull.uid == UID
		occupationFull.name == NAME
		occupationFull.legalOccupation == LEGAL_OCCUPATION
		occupationFull.medicalOccupation == MEDICAL_OCCUPATION
		occupationFull.scientificOccupation == SCIENTIFIC_OCCUPATION
		occupationFull.characters.size() == occupation.characters.size()
	}

}
