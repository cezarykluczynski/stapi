package com.cezarykluczynski.stapi.auth.api_key.operation.edit

import org.apache.commons.lang3.StringUtils
import spock.lang.Specification

class ApiKeyEditValidatorTest extends Specification {

	private ApiKeyEditValidator apiKeyEditValidator

	void setup() {
		apiKeyEditValidator = new ApiKeyEditValidator()
	}

	void "validates URL length"() {
		expect:
		apiKeyEditValidator.isUrlShortEnough(null)
		apiKeyEditValidator.isUrlShortEnough('')
		apiKeyEditValidator.isUrlShortEnough('http://stapi.co/')
		apiKeyEditValidator.isUrlShortEnough(StringUtils.leftPad('', 256, 'a'))
		!apiKeyEditValidator.isUrlShortEnough(StringUtils.leftPad('', 257, 'a'))
	}

	void "validates description length"() {
		expect:
		apiKeyEditValidator.isDescriptionShortEnough(null)
		apiKeyEditValidator.isDescriptionShortEnough('')
		apiKeyEditValidator.isDescriptionShortEnough(StringUtils.leftPad('', 4000, 'a'))
		!apiKeyEditValidator.isDescriptionShortEnough(StringUtils.leftPad('', 4001, 'a'))
	}

}
