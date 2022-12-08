package com.cezarykluczynski.stapi.server.occupation.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationBase
import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationV2Base
import com.cezarykluczynski.stapi.model.occupation.dto.OccupationRequestDTO
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import com.cezarykluczynski.stapi.server.occupation.dto.OccupationRestBeanParams
import com.cezarykluczynski.stapi.server.occupation.dto.OccupationV2RestBeanParams
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

	void "maps OccupationV2RestBeanParams to OccupationRequestDTO"() {
		given:
		OccupationV2RestBeanParams occupationRestBeanParams = new OccupationV2RestBeanParams(
				uid: UID,
				name: NAME,
				artsOccupation: ARTS_OCCUPATION,
				communicationOccupation: COMMUNICATION_OCCUPATION,
				economicOccupation: ECONOMIC_OCCUPATION,
				educationOccupation: EDUCATION_OCCUPATION,
				entertainmentOccupation: ENTERTAINMENT_OCCUPATION,
				illegalOccupation: ILLEGAL_OCCUPATION,
				legalOccupation: LEGAL_OCCUPATION,
				medicalOccupation: MEDICAL_OCCUPATION,
				scientificOccupation: SCIENTIFIC_OCCUPATION,
				sportsOccupation: SPORTS_OCCUPATION,
				victualOccupation: VICTUAL_OCCUPATION)

		when:
		OccupationRequestDTO occupationRequestDTO = occupationBaseRestMapper.mapV2Base occupationRestBeanParams

		then:
		occupationRequestDTO.uid == UID
		occupationRequestDTO.name == NAME
		occupationRequestDTO.artsOccupation == ARTS_OCCUPATION
		occupationRequestDTO.communicationOccupation == COMMUNICATION_OCCUPATION
		occupationRequestDTO.economicOccupation == ECONOMIC_OCCUPATION
		occupationRequestDTO.educationOccupation == EDUCATION_OCCUPATION
		occupationRequestDTO.entertainmentOccupation == ENTERTAINMENT_OCCUPATION
		occupationRequestDTO.illegalOccupation == ILLEGAL_OCCUPATION
		occupationRequestDTO.legalOccupation == LEGAL_OCCUPATION
		occupationRequestDTO.medicalOccupation == MEDICAL_OCCUPATION
		occupationRequestDTO.scientificOccupation == SCIENTIFIC_OCCUPATION
		occupationRequestDTO.sportsOccupation == SPORTS_OCCUPATION
		occupationRequestDTO.victualOccupation == VICTUAL_OCCUPATION
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

	void "maps DB entity to base REST V2 entity"() {
		given:
		Occupation occupation = createOccupation()

		when:
		OccupationV2Base occupationV2Base = occupationBaseRestMapper.mapV2Base(occupation)

		then:
		occupationV2Base.uid == UID
		occupationV2Base.name == NAME
		occupationV2Base.artsOccupation == ARTS_OCCUPATION
		occupationV2Base.communicationOccupation == COMMUNICATION_OCCUPATION
		occupationV2Base.economicOccupation == ECONOMIC_OCCUPATION
		occupationV2Base.educationOccupation == EDUCATION_OCCUPATION
		occupationV2Base.entertainmentOccupation == ENTERTAINMENT_OCCUPATION
		occupationV2Base.illegalOccupation == ILLEGAL_OCCUPATION
		occupationV2Base.legalOccupation == LEGAL_OCCUPATION
		occupationV2Base.medicalOccupation == MEDICAL_OCCUPATION
		occupationV2Base.scientificOccupation == SCIENTIFIC_OCCUPATION
		occupationV2Base.sportsOccupation == SPORTS_OCCUPATION
		occupationV2Base.victualOccupation == VICTUAL_OCCUPATION
	}

}
