package com.cezarykluczynski.stapi.auth.api_key.operation.creation

import com.google.common.collect.Sets
import spock.lang.Specification

import java.util.regex.Pattern

class ApiKeyGeneratorTest extends Specification {

	private static final Pattern API_KEY_PATTERN = Pattern.compile('[a-f0-9]{40}')

	private ApiKeyGenerator apiKeyGenerator

	void setup() {
		apiKeyGenerator = new ApiKeyGenerator()
	}

	void "creates unique api keys"() {
		given:
		Set<String> apiKeySet = Sets.newHashSet()

		when:
		for (int i = 0; i < 1000; i++) {
			apiKeySet.add apiKeyGenerator.generate()
		}

		then:
		apiKeySet.size() == 1000
		apiKeySet.stream().allMatch { API_KEY_PATTERN.matcher(it).matches() }
	}

}
