package com.cezarykluczynski.stapi.server.reference.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.Reference as RestReference
import com.cezarykluczynski.stapi.model.reference.entity.Reference as ModelReference
import org.mapstruct.factory.Mappers

class ReferenceRestMapperTest extends AbstractReferenceMapperTest {

	private ReferenceRestMapper referenceRestMapper

	void setup() {
		referenceRestMapper = Mappers.getMapper(ReferenceRestMapper)
	}

	void "maps db entity to REST entity"() {
		given:
		ModelReference modelReference = new ModelReference(
				uid: UID,
				referenceType: REFERENCE_TYPE,
				referenceNumber: REFERENCE_NUMBER,
		)

		when:
		RestReference restReference = referenceRestMapper.map(modelReference)

		then:
		restReference.uid == UID
		restReference.referenceType == REST_REFERENCE_TYPE
		restReference.referenceNumber == REFERENCE_NUMBER
	}

}
