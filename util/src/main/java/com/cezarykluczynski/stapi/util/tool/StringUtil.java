package com.cezarykluczynski.stapi.util.tool;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
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

	public static boolean containsAllIgnoreCase(String subject, List<String> candidates) {
		if (subject == null || candidates == null) {
			return false;
		}

		String subjectLowerCase = subject.toLowerCase();
		return getLowerCandidatesStream(candidates).allMatch(subjectLowerCase::contains) && getLowerCandidatesStream(candidates).count() > 0;
	}

	public static List<Integer> getAllSubstringPositions(String subject, String search) {
		List<Integer> positionList = Lists.newArrayList();

		if (StringUtils.isBlank(search)) {
			return positionList;
		}

		int currentIndex = subject.indexOf(search);

		if (currentIndex == -1) {
			return positionList;
		}

		positionList.add(currentIndex);

		while (currentIndex >= 0) {
			currentIndex = subject.indexOf(search, currentIndex + search.length());
			if (currentIndex != -1) {
				positionList.add(currentIndex);
			}
		}

		return positionList;
	}

	public static boolean anyStartsWithIgnoreCase(List<String> stringList, String prefix) {
		return stringList.stream().anyMatch(categoryTitle -> StringUtils.startsWithIgnoreCase(categoryTitle, prefix));
	}

	public static boolean anyEndsWithIgnoreCase(List<String> stringList, String suffix) {
		return stringList.stream().anyMatch(categoryTitle -> StringUtils.endsWithIgnoreCase(categoryTitle, suffix));
	}

	public static boolean endsWithAny(String subject, List<String> suffixList) {
		return suffixList.stream().anyMatch(suffix -> StringUtils.isNotEmpty(suffix) && subject.endsWith(suffix));
	}

	public static String substringBeforeAny(String subject, List<String> suffixList) {
		String result = subject;

		for (String suffix : suffixList) {
			result = StringUtils.substringBefore(result, suffix);
		}

		return result;
	}

	private static Stream<String> getLowerCandidatesStream(List<String> candidates) {
		List<String> candidatesLowerCase = candidates.stream()
				.filter(StringUtils::isNotBlank)
				.map(String::toLowerCase)
				.collect(Collectors.toList());

		return candidatesLowerCase.stream();
	}

	public static boolean containsIgnoreCase(Collection<String> stringCollection, String lookup) {
		return stringCollection.stream().anyMatch(string -> StringUtils.equalsIgnoreCase(string, lookup));
	}

}
