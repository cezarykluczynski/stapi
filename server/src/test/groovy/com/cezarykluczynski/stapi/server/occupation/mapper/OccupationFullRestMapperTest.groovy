package com.cezarykluczynski.stapi.server.occupation.mapper

import com.cezarykluczynski.stapi.client.rest.model.OccupationFull
import com.cezarykluczynski.stapi.client.rest.model.OccupationV2Full
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

	void "maps DB entity to full REST V2 entity"() {
		given:
		Occupation occupation = createOccupation()

		when:
		OccupationV2Full occupationV2Full = occupationFullRestMapper.mapV2Full(occupation)

		then:
		occupationV2Full.uid == UID
		occupationV2Full.name == NAME
		occupationV2Full.artsOccupation == ARTS_OCCUPATION
		occupationV2Full.communicationOccupation == COMMUNICATION_OCCUPATION
		occupationV2Full.economicOccupation == ECONOMIC_OCCUPATION
		occupationV2Full.educationOccupation == EDUCATION_OCCUPATION
		occupationV2Full.entertainmentOccupation == ENTERTAINMENT_OCCUPATION
		occupationV2Full.illegalOccupation == ILLEGAL_OCCUPATION
		occupationV2Full.legalOccupation == LEGAL_OCCUPATION
		occupationV2Full.medicalOccupation == MEDICAL_OCCUPATION
		occupationV2Full.scientificOccupation == SCIENTIFIC_OCCUPATION
		occupationV2Full.sportsOccupation == SPORTS_OCCUPATION
		occupationV2Full.victualOccupation == VICTUAL_OCCUPATION
		occupationV2Full.characters.size() == occupation.characters.size()
	}

}
