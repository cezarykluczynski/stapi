package com.cezarykluczynski.stapi.auth.api_key.operation.read

import org.springframework.data.domain.Page
import spock.lang.Specification

class ApiKeyPageAccountSpecificationTest extends Specification {

	private ApiKeyPageAccountSpecification apiKeyPageAccountSpecification

	void setup() {
		apiKeyPageAccountSpecification = new ApiKeyPageAccountSpecification()
	}

	void "returns true for page with only one total page"() {
		given:
		Page page = Mock()

		when:
		boolean isSatisfiedBy = apiKeyPageAccountSpecification.isSatisfiedBy(page)

		then:
		1 * page.totalPages >> 1
		0 * _
		isSatisfiedBy
	}

	void "returns false for page with total pages number other than 1"() {
		given:
		boolean isSatisfiedBy
		Page page = Mock()

		when:
		isSatisfiedBy = apiKeyPageAccountSpecification.isSatisfiedBy(page)

		then:
		1 * page.totalPages >> 0
		0 * _
		!isSatisfiedBy

		when:
		isSatisfiedBy = apiKeyPageAccountSpecification.isSatisfiedBy(page)

		then:
		1 * page.totalPages >> 2
		0 * _
		!isSatisfiedBy
	}

}
