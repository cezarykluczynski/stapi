package com.cezarykluczynski.stapi.etl.util

import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class SortingUtilTest extends Specification {

	private static final String TITLE_1 = 'TITLE_1'
	private static final String TITLE_2 = 'TITLE_2'
	private static final String TITLE_3 = 'TITLE_3'

	void "sorts PageHeaders while removing duplicates"() {
		expect:
		SortingUtil.sortedUnique([of(TITLE_2), of(TITLE_3), of(TITLE_1), of(TITLE_2)]) == [of(TITLE_1), of(TITLE_2), of(TITLE_3)]
	}

	private static PageHeader of(String title) {
		new PageHeader(title: title)
	}

}
