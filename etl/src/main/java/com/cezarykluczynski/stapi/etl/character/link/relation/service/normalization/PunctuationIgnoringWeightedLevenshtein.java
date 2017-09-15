package com.cezarykluczynski.stapi.etl.character.link.relation.service.normalization;

import com.google.common.collect.Lists;
import info.debatty.java.stringsimilarity.WeightedLevenshtein;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class PunctuationIgnoringWeightedLevenshtein extends WeightedLevenshtein {

	private static final List<String> PUNCTUATION = Lists.newArrayList(" ", ",", ".", "-", ";");

	PunctuationIgnoringWeightedLevenshtein() {
		super((c1, c2) -> PUNCTUATION.contains(String.valueOf(c1)) && PUNCTUATION.contains(String.valueOf(c2)) ? 0 : 1);
	}

}
