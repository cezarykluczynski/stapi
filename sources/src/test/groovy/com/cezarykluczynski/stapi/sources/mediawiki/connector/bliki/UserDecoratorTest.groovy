package com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki

import spock.lang.Specification

class UserDecoratorTest extends Specification {

	def "connector is exposed"() {
		when:
		UserDecorator userDecorator = new UserDecorator("", "", "")

		then:
		userDecorator.connector != null
	}

}
