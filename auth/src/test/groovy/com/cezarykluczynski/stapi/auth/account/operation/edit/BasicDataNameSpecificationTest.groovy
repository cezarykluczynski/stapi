package com.cezarykluczynski.stapi.auth.account.operation.edit

import spock.lang.Specification

class BasicDataNameSpecificationTest extends Specification {

	private BasicDataNameSpecification basicDataNameSpecification

	void setup() {
		basicDataNameSpecification = new BasicDataNameSpecification()
	}

	void "detect valid name"() {
		expect:
		basicDataNameSpecification.isSatisfiedBy('Ed')
		basicDataNameSpecification.isSatisfiedBy('Cezary')
		basicDataNameSpecification.isSatisfiedBy('Cezary Kluczyński')
	}

	void "detect invalid email"() {
		expect:
		!basicDataNameSpecification.isSatisfiedBy('')
		!basicDataNameSpecification.isSatisfiedBy('.')
		!basicDataNameSpecification.isSatisfiedBy('D')
	}

}
