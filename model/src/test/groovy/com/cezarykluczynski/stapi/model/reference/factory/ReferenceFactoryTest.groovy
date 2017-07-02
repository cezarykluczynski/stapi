package com.cezarykluczynski.stapi.model.reference.factory

import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.reference.entity.enums.ReferenceType
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import spock.lang.Specification
import spock.lang.Unroll

class ReferenceFactoryTest extends Specification {

	private ReferenceFactory referenceFactory

	void setup() {
		referenceFactory = new ReferenceFactory()
	}

	void "required UID to be 14 characters long"() {
		when:
		referenceFactory.createFromUid('1234567890abc')

		then:
		thrown(IllegalArgumentException)

		when:
		referenceFactory.createFromUid('1234567890abcde')

		then:
		thrown(IllegalArgumentException)
	}

	@Unroll('create #reference from #uid')
	void "creates reference from uid"() {
		expect:
		reference == referenceFactory.createFromUid(uid)

		where:
		reference                                                                                                 | uid
		new Reference(uid: 'ISBN1234567890', referenceType: ReferenceType.ISBN, referenceNumber: '1234567890')    | 'ISBN1234567890'
		new Reference(uid: 'I1234567890123', referenceType: ReferenceType.ISBN, referenceNumber: '1234567890123') | 'I1234567890123'
		new Reference(uid: 'I123456789012X', referenceType: ReferenceType.ISBN, referenceNumber: '123456789012X') | 'I123456789012X'
		new Reference(uid: 'ASINB001PUYIGQ', referenceType: ReferenceType.ASIN, referenceNumber: 'B001PUYIGQ')    | 'ASINB001PUYIGQ'
		new Reference(uid: 'ISCNA130401060', referenceType: ReferenceType.ISRC, referenceNumber: 'CNA130401060')  | 'ISCNA130401060'
		new Reference(uid: 'E7332431036161', referenceType: ReferenceType.EAN, referenceNumber: '7332431036161')  | 'E7332431036161'
		new Reference(uid: 'EAN80096385074', referenceType: ReferenceType.EAN, referenceNumber: '96385074')       | 'EAN80096385074'
	}

	void "throws exception when invalid UID is passed"() {
		when:
		referenceFactory.createFromUid('AAAA1234567890')

		then:
		thrown(StapiRuntimeException)
	}

}
