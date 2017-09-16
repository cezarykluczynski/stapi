package com.cezarykluczynski.stapi.etl.character.link.relation.service.normalization;

import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
class CharacterRelationRelativeNormalizationService extends AbstractCharacterRelationNormalizationService {

	@Inject
	CharacterRelationRelativeNormalizationService(PunctuationIgnoringWeightedLevenshtein punctuationIgnoringWeightedLevenshtein) {
		super(punctuationIgnoringWeightedLevenshtein);
	}

}
