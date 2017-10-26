package com.cezarykluczynski.stapi.etl.character.link.relation.service.normalization;

import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterRelationName;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
class CharacterRelationChildrenNormalizationService extends AbstractCharacterRelationNormalizationService {

	private static final String STEP_SON = "Step-son";
	private static final String[] ADOPTIVE_CHILDREN_ADJECTIVE = {"adopted", "adoptive"};

	CharacterRelationChildrenNormalizationService(PunctuationIgnoringWeightedLevenshtein punctuationIgnoringWeightedLevenshtein) {
		super(punctuationIgnoringWeightedLevenshtein);
	}

	@Override
	String normalize(String rawRelationName) {
		String relationName = super.normalize(rawRelationName);

		if (relationName != null) {
			return relationName;
		}

		if (StringUtils.equalsIgnoreCase(rawRelationName, STEP_SON)) {
			return CharacterRelationName.STEPSON;
		}

		if (StringUtils.equalsAnyIgnoreCase(rawRelationName, ADOPTIVE_CHILDREN_ADJECTIVE)) {
			return CharacterRelationName.ADOPTIVE_CHILD;
		}

		return null;
	}
}
