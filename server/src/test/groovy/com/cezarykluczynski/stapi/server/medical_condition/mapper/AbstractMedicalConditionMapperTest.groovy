package com.cezarykluczynski.stapi.server.medical_condition.mapper

import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition
import com.cezarykluczynski.stapi.util.AbstractMedicalConditionTest

abstract class AbstractMedicalConditionMapperTest extends AbstractMedicalConditionTest {

	protected static MedicalCondition createMedicalCondition() {
		new MedicalCondition(
				uid: UID,
				name: NAME,
				psychologicalCondition: PSYCHOLOGICAL_CONDITION)
	}

}
