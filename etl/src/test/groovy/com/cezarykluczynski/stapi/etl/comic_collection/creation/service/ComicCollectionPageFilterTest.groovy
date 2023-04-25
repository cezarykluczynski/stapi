package com.cezarykluczynski.stapi.etl.comic_collection.creation.service

import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import spock.lang.Specification

class ComicCollectionPageFilterTest extends Specification {

	ComicCollectionPageFilter comicCollectionPageFilter

	void setup() {
		comicCollectionPageFilter = new ComicCollectionPageFilter()
	}

	void "returns true when page title is among invalid titles"() {
		given:
		Page page = new Page(title: RandomUtil.randomItem(ComicCollectionPageFilter.INVALID_TITLES))

		when:
		boolean shouldBeFilteredOut = comicCollectionPageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns false otherwise"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = comicCollectionPageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		!shouldBeFilteredOut
	}

}
