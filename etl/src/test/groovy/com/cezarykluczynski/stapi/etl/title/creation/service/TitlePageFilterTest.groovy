package com.cezarykluczynski.stapi.etl.title.creation.service

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class TitlePageFilterTest extends Specification {

	private CategorySortingService categorySortingServiceMock

	private TitleListCache titleListCacheMock

	private TitlePageFilter titlePageFilter

	void setup() {
		categorySortingServiceMock = Mock()
		titleListCacheMock = Mock()
		titlePageFilter = new TitlePageFilter(categorySortingServiceMock, titleListCacheMock)
	}

	void "when page title is among titles to filter out"() {
		given:
		Page page = new Page(title: RandomUtil.randomItem(TitlePageFilter.TITLES_TO_FILTER_OUT))

		when:
		boolean shouldBeFilteredOut = titlePageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "when page is sorted on top of any category, true is returned"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = titlePageFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> true
		0 * _
		shouldBeFilteredOut
	}

	void "when page is sorted on top of any category, and contains 'ranks', it is added to cache, then true is returned"() {
		given:
		Page page = new Page(title: 'Romulan ranks')

		when:
		boolean shouldBeFilteredOut = titlePageFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> true
		1 * titleListCacheMock.add(page)
		0 * _
		shouldBeFilteredOut
	}

	void "when page is a product of redirect, true is returned"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeader))

		when:
		boolean shouldBeFilteredOut = titlePageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "when false when page is not an effect of redirect, and is not sorted on top of any category"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = titlePageFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> false
		0 * _
		!shouldBeFilteredOut
	}

}
