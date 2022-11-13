package com.cezarykluczynski.stapi.etl.common.service

import spock.lang.Specification

class ParagraphExtractorTest extends Specification {

	private static final String LINE_1 = 'PARAGRAPH_1'
	private static final String LINE_2 = 'PARAGRAPH_2'
	private static final String LINE_3 = 'PARAGRAPH_3'
	private static final String TWO_LINE_SEPARATOR = '\n\n'
	private static final String ONE_LINE_SEPARATOR = '\n'
	private static final String WIKITEXT_WITH_PARAGRAPHS = LINE_1 + TWO_LINE_SEPARATOR + LINE_2 + TWO_LINE_SEPARATOR + LINE_3
	private static final String WIKITEXT_WITHOUT_PARAGRAPHS = 'WIKITEXT_WITHOUT_PARAGRAPHS'
	private static final String WIKITEXT_WITH_LINES = LINE_1 + ONE_LINE_SEPARATOR + LINE_2 + ONE_LINE_SEPARATOR + LINE_3
	private static final String WIKITEXT_WITHOUT_LINES = 'WIKITEXT_WITHOUT_PARAGRAPHS'

	private ParagraphExtractor paragraphExtractor

	void setup() {
		paragraphExtractor = new ParagraphExtractor()
	}

	void "extracts paragraphs"() {
		when:
		List<String> paragraphList = paragraphExtractor.extractParagraphs(WIKITEXT_WITH_PARAGRAPHS)

		then:
		paragraphList.size() == 3
		paragraphList[0] == LINE_1
		paragraphList[1] == LINE_2
		paragraphList[2] == LINE_3
	}

	void "returns original value when no paragraph separator is found"() {
		when:
		List<String> paragraphList = paragraphExtractor.extractParagraphs(WIKITEXT_WITHOUT_PARAGRAPHS)

		then:
		paragraphList.size() == 1
		paragraphList[0] == WIKITEXT_WITHOUT_PARAGRAPHS
	}

	void "(paragraphs) returns empty list when null is passed"() {
		when:
		List<String> paragraphList = paragraphExtractor.extractParagraphs(null)

		then:
		paragraphList.size() == 0
	}

	void "extracts lines"() {
		when:
		List<String> paragraphList = paragraphExtractor.extractLines(WIKITEXT_WITH_LINES)

		then:
		paragraphList.size() == 3
		paragraphList[0] == LINE_1
		paragraphList[1] == LINE_2
		paragraphList[2] == LINE_3
	}

	void "returns original value when no line separator is found"() {
		when:
		List<String> paragraphList = paragraphExtractor.extractLines(WIKITEXT_WITHOUT_LINES)

		then:
		paragraphList.size() == 1
		paragraphList[0] == WIKITEXT_WITHOUT_LINES
	}

	void "(lines) returns empty list when null is passed"() {
		when:
		List<String> paragraphList = paragraphExtractor.extractLines(null)

		then:
		paragraphList.size() == 0
	}

}
