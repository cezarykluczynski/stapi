package com.cezarykluczynski.stapi.server.occupation.mapper

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import com.cezarykluczynski.stapi.util.AbstractOccupationTest

abstract class AbstractOccupationMapperTest extends AbstractOccupationTest {

	protected Occupation createOccupation() {
		new Occupation(
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
				victualOccupation: VICTUAL_OCCUPATION,
				characters: createSetOfRandomNumberOfMocks(Character))
	}

}
