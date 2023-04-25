package com.cezarykluczynski.stapi.etl.mediawiki.connector.bliki

import spock.lang.Specification

class UserDecoratorTest extends Specification {

	void "connector is exposed"() {
		when:
		UserDecorator userDecorator = new UserDecorator('', '', '')

		then:
		userDecorator.connector != null
	}

}
