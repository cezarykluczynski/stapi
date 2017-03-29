package com.cezarykluczynski.stapi.etl.common.processor

import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class CategoryTitlesExtractingProcessorTest extends Specification {

	private static final String CATEGORY_TITLE_1_WITH_SPACE = 'CATEGORY_TITLE 1'
	private static final String CATEGORY_TITLE_1_NO_SPACE = 'CATEGORY_TITLE_1'
	private static final String CATEGORY_TITLE_2 = 'CATEGORY_TITLE_2'

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor

	void setup() {
		categoryTitlesExtractingProcessor = new CategoryTitlesExtractingProcessor()
	}

	void "extracts titles from CategoryHeader list"() {
		when:
		List<String> categoryTitleList = categoryTitlesExtractingProcessor.process(Lists.newArrayList(
				new CategoryHeader(title: CATEGORY_TITLE_1_WITH_SPACE),
				new CategoryHeader(title: CATEGORY_TITLE_2)
		))

		then:
		categoryTitleList.size() == 2
		categoryTitleList[0] == CATEGORY_TITLE_1_NO_SPACE
		categoryTitleList[1] == CATEGORY_TITLE_2
	}

}
