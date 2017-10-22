package com.cezarykluczynski.stapi.server.occupation.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationBase
import com.cezarykluczynski.stapi.model.occupation.dto.OccupationRequestDTO
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import com.cezarykluczynski.stapi.server.occupation.dto.OccupationRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class OccupationBaseRestMapperTest extends AbstractOccupationMapperTest {

	private OccupationBaseRestMapper occupationBaseRestMapper

	void setup() {
		occupationBaseRestMapper = Mappers.getMapper(OccupationBaseRestMapper)
	}

	void "maps OccupationRestBeanParams to OccupationRequestDTO"() {
		given:
		OccupationRestBeanParams occupationRestBeanParams = new OccupationRestBeanParams(
				uid: UID,
				name: NAME,
				legalOccupation: LEGAL_OCCUPATION,
				medicalOccupation: MEDICAL_OCCUPATION,
				scientificOccupation: SCIENTIFIC_OCCUPATION)

		when:
		OccupationRequestDTO occupationRequestDTO = occupationBaseRestMapper.mapBase occupationRestBeanParams

		then:
		occupationRequestDTO.uid == UID
		occupationRequestDTO.name == NAME
		occupationRequestDTO.legalOccupation == LEGAL_OCCUPATION
		occupationRequestDTO.medicalOccupation == MEDICAL_OCCUPATION
		occupationRequestDTO.scientificOccupation == SCIENTIFIC_OCCUPATION
	}

	void "maps DB entity to base REST entity"() {
		given:
		Occupation occupation = createOccupation()

		when:
		OccupationBase occupationBase = occupationBaseRestMapper.mapBase(Lists.newArrayList(occupation))[0]

		then:
		occupationBase.uid == UID
		occupationBase.name == NAME
		occupationBase.legalOccupation == LEGAL_OCCUPATION
		occupationBase.medicalOccupation == MEDICAL_OCCUPATION
		occupationBase.scientificOccupation == SCIENTIFIC_OCCUPATION
	}

}
