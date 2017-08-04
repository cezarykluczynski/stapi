package com.cezarykluczynski.stapi.etl.genre.service;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreNameNormalizationService {

	private static final String SLASH = "/";
	private static final String DASH = "-";

	public String normalize(String genreName) {
		String normalizedGenreName = StringUtils.replace(genreName, SLASH, StringUtils.SPACE);
		return normalizeUsing(normalizeUsing(normalizedGenreName, DASH), StringUtils.SPACE);
	}

	private String normalizeUsing(String genreName, String separator) {
		List<String> words = Lists.newArrayList(StringUtils.split(genreName, separator));
		List<String> normalizedWords = Lists.newArrayList();

		for (int i = 0; i < words.size(); i++) {
			String word = words.get(i);
			String normalizedWord = i == 0 ? StringUtils.capitalize(word) : maybeUncapitalize(word);
			normalizedWords.add(normalizedWord);
		}

		return Joiner.on(separator).join(normalizedWords);
	}

	private String maybeUncapitalize(String word) {
		return StringUtils.isAllUpperCase(word) ? word : StringUtils.uncapitalize(word);
	}

}
