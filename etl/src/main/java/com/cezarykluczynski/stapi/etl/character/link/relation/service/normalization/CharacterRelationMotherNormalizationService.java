package com.cezarykluczynski.stapi.etl.character.link.relation.service.normalization;

import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterRelationName;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
class CharacterRelationMotherNormalizationService extends AbstractCharacterRelationNormalizationService {

	private static final String FOSTER = "foster";
	private static final String STEP = "step-";
	private static final String SURROGATE = "surrogate";

	CharacterRelationMotherNormalizationService(PunctuationIgnoringWeightedLevenshtein punctuationIgnoringWeightedLevenshtein) {
		super(punctuationIgnoringWeightedLevenshtein);
	}

	@Override
	@SuppressWarnings("ReturnCount")
	String normalize(String rawRelationName) {
		String relationName = super.normalize(rawRelationName);

		if (relationName != null) {
			return relationName;
		}

		if (StringUtils.containsIgnoreCase(rawRelationName, ADOPT)) {
			return CharacterRelationName.ADOPTIVE_MOTHER;
		}

		if (StringUtils.containsIgnoreCase(rawRelationName, FOSTER)) {
			return CharacterRelationName.FOSTER_MOTHER;
		}

		if (StringUtils.containsIgnoreCase(rawRelationName, STEP)) {
			return CharacterRelationName.STEPMOTHER;
		}

		if (StringUtils.containsIgnoreCase(rawRelationName, SURROGATE)) {
			return CharacterRelationName.SURROGATE_MOTHER;
		}

		return null;
	}
}
