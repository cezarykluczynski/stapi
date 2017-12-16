package com.cezarykluczynski.stapi.etl.location.creation.service

import com.cezarykluczynski.stapi.etl.common.service.CategoryFinder
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class LocationPageFilterTest extends Specification {

	private static final String TITLE = 'TITLE'

	private CategoryFinder categoryFinderMock

	private LocationNameFilter locationNameFilterMock

	private LocationPageFilter locationPageFilter

	void setup() {
		categoryFinderMock = Mock()
		locationNameFilterMock = Mock()
		locationPageFilter = new LocationPageFilter(categoryFinderMock, locationNameFilterMock)
	}

	void "returns true when redirect path is not empty"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeader))

		when:
		boolean shouldBeFilteredOut = locationPageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page contains Organization's category"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = locationPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryFinderMock.hasAnyCategory(page, CategoryTitles.ORGANIZATIONS) >> true
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page contains Lists category"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = locationPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryFinderMock.hasAnyCategory(page, CategoryTitles.ORGANIZATIONS) >> false
		1 * categoryFinderMock.hasAnyCategory(page, Lists.newArrayList(CategoryTitle.LISTS)) >> true
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when LocationNameFilter returns false"() {
		given:
		Page page = new Page(title: TITLE)

		when:
		boolean shouldBeFilteredOut = locationPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryFinderMock.hasAnyCategory(page, CategoryTitles.ORGANIZATIONS) >> false
		1 * categoryFinderMock.hasAnyCategory(page, Lists.newArrayList(CategoryTitle.LISTS)) >> false
		1 * locationNameFilterMock.isLocation(TITLE) >> LocationNameFilter.Match.IS_NOT_A_LOCATION
		0 * _
		shouldBeFilteredOut
	}

	void "returns false when LocationNameFilter returns null"() {
		given:
		Page page = new Page(title: TITLE)

		when:
		boolean shouldBeFilteredOut = locationPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categoryFinderMock.hasAnyCategory(page, CategoryTitles.ORGANIZATIONS) >> false
		1 * categoryFinderMock.hasAnyCategory(page, Lists.newArrayList(CategoryTitle.LISTS)) >> false
		1 * locationNameFilterMock.isLocation(TITLE) >> LocationNameFilter.Match.UNKNOWN_RESULT
		0 * _
		!shouldBeFilteredOut
	}

}
