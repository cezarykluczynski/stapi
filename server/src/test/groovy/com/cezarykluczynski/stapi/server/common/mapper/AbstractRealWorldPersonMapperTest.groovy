package com.cezarykluczynski.stapi.server.common.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.Gender as GenderEnumRest
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender
import com.cezarykluczynski.stapi.util.AbstractRealWorldPersonTest

abstract class AbstractRealWorldPersonMapperTest extends AbstractRealWorldPersonTest {

	static final GenderEnumRest GENDER_ENUM_REST = GenderEnumRest.F
	static final Gender GENDER = Gender.F

}
