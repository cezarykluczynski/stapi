package com.cezarykluczynski.stapi.util.tool;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringUtil {

	public static boolean startsWithAnyIgnoreCase(String subject, List<String> candidates) {
		if (subject == null || candidates == null) {
			return false;
		}

		String subjectLowerCase = subject.toLowerCase();
		return getLowerCandidatesStream(candidates).anyMatch(subjectLowerCase::startsWith);
	}

	public static boolean containsAnyIgnoreCase(String subject, List<String> candidates) {
		if (subject == null || candidates == null) {
			return false;
		}

		String subjectLowerCase = subject.toLowerCase();
		return getLowerCandidatesStream(candidates).anyMatch(subjectLowerCase::contains);
	}

	private static Stream<String> getLowerCandidatesStream(List<String> candidates) {
		List<String> candidatesLowerCase = candidates.stream()
				.filter(StringUtils::isNotBlank)
				.map(String::toLowerCase)
				.collect(Collectors.toList());

		return candidatesLowerCase.stream();
	}

}
