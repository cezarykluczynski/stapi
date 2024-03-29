package com.cezarykluczynski.stapi.server.reference.mapper

import com.cezarykluczynski.stapi.client.rest.model.ReferenceType as RestReferenceType
import com.cezarykluczynski.stapi.model.reference.entity.enums.ReferenceType
import com.cezarykluczynski.stapi.util.AbstractReferenceTest

abstract class AbstractReferenceMapperTest extends AbstractReferenceTest {

	protected static final RestReferenceType REST_REFERENCE_TYPE = RestReferenceType.ASIN
	protected static final ReferenceType REFERENCE_TYPE = ReferenceType.ASIN

}
