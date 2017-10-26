package com.cezarykluczynski.stapi.etl.character.link.relation.service.normalization;

import com.cezarykluczynski.stapi.etl.character.link.relation.dto.CharacterRelationName;
import com.cezarykluczynski.stapi.util.tool.StringUtil;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class CharacterRelationSpouseNormalizationService extends AbstractCharacterRelationNormalizationService {

	private static final List<String> EXES = Lists.newArrayList("divorced", "ex", "former");

	CharacterRelationSpouseNormalizationService(PunctuationIgnoringWeightedLevenshtein punctuationIgnoringWeightedLevenshtein) {
		super(punctuationIgnoringWeightedLevenshtein);
	}

	String normalize(String rawRelationName) {
		String relationName = super.normalize(rawRelationName);

		if (relationName != null) {
			return relationName;
		}

		if (StringUtil.containsAnyIgnoreCase(rawRelationName, EXES)) {
			return CharacterRelationName.EX_SPOUSE;
		}

		if (StringUtils.containsIgnoreCase(rawRelationName, CharacterRelationName.WIFE)) {
			return CharacterRelationName.WIFE;
		}

		return null;
	}

}
