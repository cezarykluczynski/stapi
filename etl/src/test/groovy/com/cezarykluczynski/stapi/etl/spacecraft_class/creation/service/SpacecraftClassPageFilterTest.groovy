package com.cezarykluczynski.stapi.etl.spacecraft_class.creation.service

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class SpacecraftClassPageFilterTest extends Specification {

	private CategorySortingService categorySortingServiceMock

	private SpacecraftClassPageFilter starshipClassFilter

	void setup() {
		categorySortingServiceMock = Mock()
		starshipClassFilter = new SpacecraftClassPageFilter(categorySortingServiceMock)
	}

	void "returns true when page is an effect of redirect"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeader))

		when:
		boolean shouldBeFilteredOut = starshipClassFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

	void "returns true when page is sorted on top of any category"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = starshipClassFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> true
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page name starts with 'Unnamed '"() {
		given:
		Page page = new Page(title: 'Unnamed Alpha and Beta Quadrant starships (22nd century)')

		when:
		boolean shouldBeFilteredOut = starshipClassFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

	void "returns false when page does not qualify to be filtered out"() {
		given:
		Page page = new Page(title: '')

		when:
		boolean shouldBeFilteredOut = starshipClassFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> false
		0 * _
		!shouldBeFilteredOut
	}

}
