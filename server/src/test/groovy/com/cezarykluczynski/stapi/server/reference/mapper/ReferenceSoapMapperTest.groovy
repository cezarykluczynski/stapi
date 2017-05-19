package com.cezarykluczynski.stapi.server.reference.mapper

import com.cezarykluczynski.stapi.client.v1.soap.Reference as SoapReference
import com.cezarykluczynski.stapi.model.reference.entity.Reference as ModelReference
import org.mapstruct.factory.Mappers

class ReferenceSoapMapperTest extends AbstractReferenceMapperTest {

	private ReferenceSoapMapper referenceSoapMapper

	void setup() {
		referenceSoapMapper = Mappers.getMapper(ReferenceSoapMapper)
	}

	void "maps db entity to SOAP entity"() {
		given:
		ModelReference modelReference = new ModelReference(
				uid: UID,
				referenceType: REFERENCE_TYPE,
				referenceNumber: REFERENCE_NUMBER,
		)

		when:
		SoapReference soapReference = referenceSoapMapper.map(modelReference)

		then:
		soapReference.uid == UID
		soapReference.referenceType == SOAP_REFERENCE_TYPE
		soapReference.referenceNumber == REFERENCE_NUMBER
	}

}
