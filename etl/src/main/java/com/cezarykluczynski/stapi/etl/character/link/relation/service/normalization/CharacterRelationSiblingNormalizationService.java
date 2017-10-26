package com.cezarykluczynski.stapi.etl.character.link.relation.service.normalization;

import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterRelationName;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
class CharacterRelationSiblingNormalizationService extends AbstractCharacterRelationNormalizationService {

	private static final String OLDER = "older";
	private static final String YOUNGER = "younger";

	CharacterRelationSiblingNormalizationService(PunctuationIgnoringWeightedLevenshtein punctuationIgnoringWeightedLevenshtein) {
		super(punctuationIgnoringWeightedLevenshtein);
	}

	@Override
	String normalize(String rawRelationName) {
		String relationName = super.normalize(rawRelationName);

		if (relationName != null) {
			return relationName;
		}

		String adoptiveRelation = tryGetAdoptiveRelation(rawRelationName);

		if (adoptiveRelation != null) {
			return adoptiveRelation;
		}

		return super.normalize(StringUtils.replaceIgnoreCase(StringUtils.replaceIgnoreCase(rawRelationName, OLDER, StringUtils.EMPTY),
				YOUNGER, StringUtils.EMPTY));
	}

	private String tryGetAdoptiveRelation(String rawRelationName) {
		if (StringUtil.containsAllIgnoreCase(rawRelationName, Lists.newArrayList(ADOPT, CharacterRelationName.BROTHER))) {
			return CharacterRelationName.ADOPTIVE_BROTHER;
		}

		if (StringUtil.containsAllIgnoreCase(rawRelationName, Lists.newArrayList(ADOPT, CharacterRelationName.SISTER))) {
			return CharacterRelationName.ADOPTIVE_SISTER;
		}

		if (StringUtils.containsIgnoreCase(rawRelationName, ADOPT)) {
			return CharacterRelationName.ADOPTIVE_SIBLING;
		}

		return null;
	}
}
