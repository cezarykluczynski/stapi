package com.cezarykluczynski.stapi.etl.common.service;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParagraphExtractor {

	public List<String> extractParagraphs(String wikitext) {
		if (wikitext == null) {
			return Lists.newArrayList();
		}

		List<String> paragraphs = Lists.newArrayList(wikitext.split("\n\n"));
		return paragraphs.isEmpty() ? Lists.newArrayList(wikitext) : paragraphs;
	}

	public List<String> extractLines(String wikitext) {
		if (wikitext == null) {
			return Lists.newArrayList();
		}

		List<String> paragraphs = Lists.newArrayList(wikitext.split("\n"));
		return paragraphs.isEmpty() ? Lists.newArrayList(wikitext) : paragraphs.stream()
				.filter(line -> !StringUtils.isWhitespace(line))
				.collect(Collectors.toList());
	}

}
