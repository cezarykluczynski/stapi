package com.cezarykluczynski.stapi.model.account.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class AccountQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private AccountQueryBuilderFactory accountQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "AccountQueryBuilderFactory is created"() {
		when:
		accountQueryBuilderFactory = new AccountQueryBuilderFactory(jpaContextMock)

		then:
		accountQueryBuilderFactory != null
	}

}
