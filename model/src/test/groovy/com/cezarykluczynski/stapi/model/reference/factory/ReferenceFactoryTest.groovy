package com.cezarykluczynski.stapi.model.reference.factory

import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.reference.entity.enums.ReferenceType
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
		reference                                                                                                  | uid
		new Reference(uid: 'ISBN1234567890', referenceType: ReferenceType.ISBN, referenceNumber: '1234567890')    | 'ISBN1234567890'
		new Reference(uid: 'I1234567890123', referenceType: ReferenceType.ISBN, referenceNumber: '1234567890123') | 'I1234567890123'
		new Reference(uid: 'I123456789012X', referenceType: ReferenceType.ISBN, referenceNumber: '123456789012X') | 'I123456789012X'
		new Reference(uid: 'ASINB001PUYIGQ', referenceType: ReferenceType.ASIN, referenceNumber: 'B001PUYIGQ')    | 'ASINB001PUYIGQ'
	}

	void "throws exception when invalid UID is passed"() {
		when:
		referenceFactory.createFromUid('AAAA1234567890')

		then:
		thrown(RuntimeException)
	}

}
