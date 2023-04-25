package com.cezarykluczynski.stapi.etl.util

import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.etl.wordpress.dto.Page
import spock.lang.Specification

class SortingUtilTest extends Specification {

	private static final String TITLE_1 = 'TITLE_1'
	private static final String TITLE_2 = 'TITLE_2'
	private static final String TITLE_3 = 'TITLE_3'

	void "sorts PageHeaders while removing duplicates"() {
		expect:
		SortingUtil.sortedUnique([of(TITLE_2), of(TITLE_3), of(TITLE_1), of(TITLE_2)]) == [of(TITLE_1), of(TITLE_2), of(TITLE_3)]
	}

	void "sorts WordPress pages while removing duplicates"() {
		given:
		List<Page> input = [wordPress(TITLE_2), wordPress(TITLE_3), wordPress(TITLE_1), wordPress(TITLE_2)]
		List<Page> output = [wordPress(TITLE_1), wordPress(TITLE_2), wordPress(TITLE_3)]

		expect:
		SortingUtil.sortedUniqueWordpressPages(input) == output
	}

	private static PageHeader of(String title) {
		new PageHeader(title: title)
	}

	private static Page wordPress(String title) {
		new Page(renderedTitle: title)
	}

}
