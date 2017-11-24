package com.cezarykluczynski.stapi.auth.account.operation.edit

import spock.lang.Specification

class BasicDataEmailSpecificationTest extends Specification {

	private BasicDataEmailSpecification basicDataEmailSpecification

	void setup() {
		basicDataEmailSpecification = new BasicDataEmailSpecification()
	}

	void "detect valid email"() {
		expect:
		basicDataEmailSpecification.isSatisfiedBy('john@example.com')
	}

	void "detect invalid email"() {
		expect:
		!basicDataEmailSpecification.isSatisfiedBy('john@exa@mple.com')
	}

}
