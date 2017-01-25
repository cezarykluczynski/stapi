package com.cezarykluczynski.stapi.etl.common.service

import spock.lang.Specification

class ParagraphExtractorTest extends Specification {

	private static final String PARAGRAPH_1 = 'PARAGRAPH_1'
	private static final String PARAGRAPH_2 = 'PARAGRAPH_2'
	private static final String PARAGRAPH_3 = 'PARAGRAPH_3'
	private static final String TWO_LINE_SEPARATOR = '\n\n'
	private static final String WIKITEXT_WITH_PARAGRAPHS = PARAGRAPH_1 + TWO_LINE_SEPARATOR + PARAGRAPH_2 + TWO_LINE_SEPARATOR + PARAGRAPH_3
	private static final String WIKITEXT_WITHOUT_PARAGRAPHS = 'WIKITEXT_WITHOUT_PARAGRAPHS'

	private ParagraphExtractor paragraphExtractor

	void setup() {
		paragraphExtractor = new ParagraphExtractor()
	}

	void "extracts paragraphs"() {
		when:
		List<String> paragraphList = paragraphExtractor.extractParagraphs(WIKITEXT_WITH_PARAGRAPHS)

		then:
		paragraphList.size() == 3
		paragraphList[0] == PARAGRAPH_1
		paragraphList[1] == PARAGRAPH_2
		paragraphList[2] == PARAGRAPH_3
	}

	void "returns original value when no separator is found"() {
		when:
		List<String> paragraphList = paragraphExtractor.extractParagraphs(WIKITEXT_WITHOUT_PARAGRAPHS)

		then:
		paragraphList.size() == 1
		paragraphList[0] == WIKITEXT_WITHOUT_PARAGRAPHS
	}

	void "returns empty list when null is passed"() {
		when:
		List<String> paragraphList = paragraphExtractor.extractParagraphs(null)

		then:
		paragraphList.size() == 0
	}

}
