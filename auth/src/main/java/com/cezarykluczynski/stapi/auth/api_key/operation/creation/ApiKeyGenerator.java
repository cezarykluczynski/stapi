package com.cezarykluczynski.stapi.auth.api_key.operation.creation;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
class ApiKeyGenerator {

	private static final Set<String> INVALID_CHARACTERS = new ImmutableSet.Builder<String>().add(":", ";", "<", "=", ">", "?", "@").build();
	private static final int API_KEY_LENGTH = 40;
	private static final RandomStringGenerator RANDOM_STRING_GENERATOR = new RandomStringGenerator.Builder()
			.withinRange(48, 70)
			.usingRandom((max) -> RandomUtils.nextInt(0, max))
			.filteredBy(character -> !INVALID_CHARACTERS.contains(Character.toString((char) character)))
			.build();

	String generate() {
		return RANDOM_STRING_GENERATOR.generate(API_KEY_LENGTH).toLowerCase();
	}

}
