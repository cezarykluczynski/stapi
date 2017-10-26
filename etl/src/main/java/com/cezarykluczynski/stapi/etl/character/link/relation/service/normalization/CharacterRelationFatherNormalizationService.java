package com.cezarykluczynski.stapi.etl.character.link.relation.service.normalization;

import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterRelationName;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
class CharacterRelationFatherNormalizationService extends AbstractCharacterRelationNormalizationService {

	private static final String FOSTER = "foster";

	CharacterRelationFatherNormalizationService(PunctuationIgnoringWeightedLevenshtein punctuationIgnoringWeightedLevenshtein) {
		super(punctuationIgnoringWeightedLevenshtein);
	}

	@Override
	String normalize(String rawRelationName) {
		String relationName = super.normalize(rawRelationName);

		if (relationName != null) {
			return relationName;
		}

		if (StringUtils.containsIgnoreCase(rawRelationName, ADOPT)) {
			return CharacterRelationName.ADOPTIVE_FATHER;
		}

		if (StringUtils.containsIgnoreCase(rawRelationName, FOSTER)) {
			return CharacterRelationName.FOSTER_FATHER;
		}

		return null;
	}
}
