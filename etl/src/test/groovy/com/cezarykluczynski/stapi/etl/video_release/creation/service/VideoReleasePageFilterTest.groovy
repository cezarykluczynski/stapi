package com.cezarykluczynski.stapi.etl.video_release.creation.service

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class VideoReleasePageFilterTest extends Specification {

	private CategorySortingService categorySortingServiceMock

	private VideoReleasePageFilter videoReleasePageFilter

	void setup() {
		categorySortingServiceMock = Mock()
		videoReleasePageFilter = new VideoReleasePageFilter(categorySortingServiceMock)
	}

	void "returns true when page title is among invalid titles"() {
		given:
		Page page = new Page(title: RandomUtil.randomItem(VideoReleasePageFilter.INVALID_TITLES))

		when:
		boolean shouldBeFilteredOut = videoReleasePageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page title has invalid prefix"() {
		given:
		Page page = new Page(title: 'Star Trek films (DVD)')

		when:
		boolean shouldBeFilteredOut = videoReleasePageFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

	void "returns true when redirect path is not empty"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeader))

		when:
		boolean shouldBeFilteredOut = videoReleasePageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page is sorted on top of any category"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = videoReleasePageFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> true
		0 * _
		shouldBeFilteredOut
	}

	void "returns false when redirect path is empty and page is not sorted on top of any category"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = videoReleasePageFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyCategory(page) >> false
		0 * _
		!shouldBeFilteredOut
	}

}
