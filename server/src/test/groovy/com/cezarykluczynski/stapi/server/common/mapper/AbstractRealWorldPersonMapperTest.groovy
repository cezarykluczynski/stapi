package com.cezarykluczynski.stapi.server.common.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.Gender as GenderEnumRest
import com.cezarykluczynski.stapi.client.v1.soap.DateRange
import com.cezarykluczynski.stapi.client.v1.soap.GenderEnum as GenderEnumSoap
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender
import com.cezarykluczynski.stapi.util.AbstractRealWorldPersonTest

abstract class AbstractRealWorldPersonMapperTest extends AbstractRealWorldPersonTest {

	static final GenderEnumSoap GENDER_ENUM_SOAP = GenderEnumSoap.F
	static final com.cezarykluczynski.stapi.client.v1.rest.model.Gender GENDER_ENUM_REST = GenderEnumRest.F
	static final Gender GENDER = Gender.F

	static final DateRange DATE_OF_BIRTH_SOAP = new DateRange(
			dateFrom: DATE_OF_BIRTH_FROM_SOAP,
			dateTo: DATE_OF_BIRTH_TO_SOAP)
	static final DateRange DATE_OF_DEATH_SOAP = new DateRange(
			dateFrom: DATE_OF_DEATH_FROM_SOAP,
			dateTo: DATE_OF_DEATH_TO_SOAP)

}
