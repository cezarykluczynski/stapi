package com.cezarykluczynski.stapi.etl.location.creation.service

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class LocationPageFilterTest extends Specification {

	private static final String TITLE = 'TITLE'

	private CategorySortingService categorySortingServiceMock

	private LocationNameFilter locationNameFilterMock

	private LocationPageFilter locationPageFilter

	void setup() {
		categorySortingServiceMock = Mock()
		locationNameFilterMock = Mock()
		locationPageFilter = new LocationPageFilter(categorySortingServiceMock, locationNameFilterMock)
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

	void "returns true when LocationNameFilter returns false"() {
		given:
		Page page = new Page(title: TITLE)

		when:
		boolean shouldBeFilteredOut = locationPageFilter.shouldBeFilteredOut(page)

		then:
		1 * locationNameFilterMock.isLocation(TITLE) >> false
		0 * _
		shouldBeFilteredOut
	}

	void "returns false when LocationNameFilter returns null"() {
		given:
		Page page = new Page(title: TITLE)

		when:
		boolean shouldBeFilteredOut = locationPageFilter.shouldBeFilteredOut(page)

		then:
		1 * locationNameFilterMock.isLocation(TITLE) >> null
		0 * _
		!shouldBeFilteredOut
	}

}
