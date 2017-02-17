package com.cezarykluczynski.stapi.server.reference.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ReferenceType as RestReferenceType
import com.cezarykluczynski.stapi.client.v1.soap.ReferenceTypeEnum as SoapReferenceType
import com.cezarykluczynski.stapi.model.reference.entity.enums.ReferenceType
import spock.lang.Specification

abstract class AbstractReferenceMapperTest extends Specification {

	protected static final String GUID = 'GUID1234567890'
	protected static final RestReferenceType REST_REFERENCE_TYPE = RestReferenceType.ASIN
	protected static final SoapReferenceType SOAP_REFERENCE_TYPE = SoapReferenceType.ASIN
	protected static final ReferenceType REFERENCE_TYPE = ReferenceType.ASIN
	protected static final String REFERENCE_NUMBER = 'ISBN1234567890'

}
