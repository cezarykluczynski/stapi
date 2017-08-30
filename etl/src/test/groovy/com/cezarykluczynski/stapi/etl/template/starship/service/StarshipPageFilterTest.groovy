package com.cezarykluczynski.stapi.etl.template.starship.service

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class StarshipPageFilterTest extends Specification {

	private CategorySortingService categorySortingServiceMock

	private StarshipPageFilter starshipPageFilter

	void setup() {
		categorySortingServiceMock = Mock()
		starshipPageFilter = new StarshipPageFilter(categorySortingServiceMock)
	}

	void "returns true when redirect path is not empty"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeader))

		when:
		boolean shouldBeFilteredOut = starshipPageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page is sorted on top of any category"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = starshipPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> true
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page name starts with 'Unnamed'"() {
		given:
		Page page = new Page(title: 'Unnamed Nova class starships')

		when:
		boolean shouldBeFilteredOut = starshipPageFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

	void "returns true when page title is on list of title to filter out"() {
		given:
		Page page = new Page(title: RandomUtil.randomItem(StarshipPageFilter.INVALID_TITLES))

		when:
		boolean shouldBeFilteredOut = starshipPageFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

}
