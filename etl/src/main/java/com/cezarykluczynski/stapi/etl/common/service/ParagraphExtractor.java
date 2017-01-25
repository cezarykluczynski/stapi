package com.cezarykluczynski.stapi.etl.common.service;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParagraphExtractor {

	public List<String> extractParagraphs(String wikitext) {
		if (wikitext == null) {
			return Lists.newArrayList();
		}

		List<String> paragraphs = Lists.newArrayList(wikitext.split("\n\n"));
		return paragraphs.isEmpty() ? Lists.newArrayList(wikitext) : paragraphs;
	}

}
