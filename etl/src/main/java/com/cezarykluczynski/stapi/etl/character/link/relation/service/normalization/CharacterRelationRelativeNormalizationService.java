package com.cezarykluczynski.stapi.etl.character.link.relation.service.normalization;

import org.springframework.stereotype.Service;

@Service
class CharacterRelationRelativeNormalizationService extends AbstractCharacterRelationNormalizationService {

	CharacterRelationRelativeNormalizationService(PunctuationIgnoringWeightedLevenshtein punctuationIgnoringWeightedLevenshtein) {
		super(punctuationIgnoringWeightedLevenshtein);
	}

}
