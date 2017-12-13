package com.cezarykluczynski.stapi.server.occupation.mapper

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import com.cezarykluczynski.stapi.util.AbstractOccupationTest

abstract class AbstractOccupationMapperTest extends AbstractOccupationTest {

	protected Occupation createOccupation() {
		new Occupation(
				uid: UID,
				name: NAME,
				legalOccupation: LEGAL_OCCUPATION,
				medicalOccupation: MEDICAL_OCCUPATION,
				scientificOccupation: SCIENTIFIC_OCCUPATION,
				characters: createSetOfRandomNumberOfMocks(Character))
	}

}
