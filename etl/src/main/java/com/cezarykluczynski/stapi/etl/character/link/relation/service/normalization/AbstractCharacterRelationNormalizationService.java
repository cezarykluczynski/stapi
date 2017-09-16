package com.cezarykluczynski.stapi.etl.character.link.relation.service.normalization;

import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterRelationName;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

abstract class AbstractCharacterRelationNormalizationService {

	static final String ADOPT = "adopt";
	private static final String DECEASED_UPPERCASE = "Deceased";
	private static final String DECEASED_LOWERCASE = "deceased";

	private final PunctuationIgnoringWeightedLevenshtein punctuationIgnoringWeightedLevenshtein;

	AbstractCharacterRelationNormalizationService(PunctuationIgnoringWeightedLevenshtein punctuationIgnoringWeightedLevenshtein) {
		this.punctuationIgnoringWeightedLevenshtein = punctuationIgnoringWeightedLevenshtein;
	}

	String normalize(String rawRelationName) {
		String trimmedRawRelationName = cleanRawRelationName(rawRelationName);
		Optional<String> matchingRelationNameOptional = CharacterRelationName.ALL_RELATIONS.stream()
				.filter(relationName -> isContainedInRawRelationName(trimmedRawRelationName, relationName))
				.findFirst();

		return matchingRelationNameOptional.orElse(null);
	}

	private String cleanRawRelationName(String rawRelationName) {
		String cleanedRawRelationName = stripWhiteCharactersAndPunctuation(rawRelationName);
		cleanedRawRelationName = StringUtils.substringBefore(StringUtils
				.substringBefore(cleanedRawRelationName, DECEASED_UPPERCASE), DECEASED_LOWERCASE);
		return stripWhiteCharactersAndPunctuation(cleanedRawRelationName);
	}

	private String stripWhiteCharactersAndPunctuation(String rawRelationName) {
		return StringUtils.strip(rawRelationName, "\"' ,.;");
	}

	private boolean isContainedInRawRelationName(String trimmedRawRelationName, String relationName) {
		return StringUtils.equalsIgnoreCase(relationName, trimmedRawRelationName)
				|| StringUtils.equalsIgnoreCase(relationName + "s", trimmedRawRelationName)
				|| punctuationIgnoringWeightedLevenshtein.distance(StringUtils.lowerCase(trimmedRawRelationName),
				StringUtils.lowerCase(relationName)) == 0;
	}
}
