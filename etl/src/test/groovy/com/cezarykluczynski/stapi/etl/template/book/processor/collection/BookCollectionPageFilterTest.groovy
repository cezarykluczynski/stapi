package com.cezarykluczynski.stapi.etl.template.book.processor.collection

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import spock.lang.Specification

class BookCollectionPageFilterTest extends Specification {

	BookCollectionPageFilter bookCollectionPageFilter

	void setup() {
		bookCollectionPageFilter = new BookCollectionPageFilter()
	}

	void "returns true when page title is among invalid titles"() {
		given:
		Page page = new Page(title: RandomUtil.randomItem(BookCollectionPageFilter.INVALID_TITLES))

		when:
		boolean shouldBeFilteredOut = bookCollectionPageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns false otherwise"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = bookCollectionPageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		!shouldBeFilteredOut
	}

}
