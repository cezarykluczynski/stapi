package com.cezarykluczynski.stapi.etl.spacecraft_type.creation.service

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class SpacecraftTypePageFilterTest extends Specification {

	CategorySortingService categorySortingServiceMock

	SpacecraftTypePageFilter spacecraftTypePageFilter

	void setup() {
		categorySortingServiceMock = Mock()
		spacecraftTypePageFilter = new SpacecraftTypePageFilter(categorySortingServiceMock)
	}

	void "returns true when redirect path is not empty"() {
		given:
		PageHeader pageHeaderRedirect = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeaderRedirect))

		when:
		boolean shouldBeFilteredOut = spacecraftTypePageFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

	void "returns true when title is among invalid titles"() {
		given:
		Page page = new Page(title: RandomUtil.randomItem(SpacecraftTypePageFilter.INVALID_TITLES))

		when:
		boolean shouldBeFilteredOut = spacecraftTypePageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page is sorted on top of 'Spacecraft_classifications' category"() {
		given:
		Page page = new Page()

		when:
		boolean shouldBeFilteredOut = spacecraftTypePageFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyOfCategories(page, Lists.newArrayList(CategoryTitle.SPACECRAFT_CLASSIFICATIONS)) >> true
		0 * _
		shouldBeFilteredOut
	}

	void "returns false for normal spacecraft type"() {
		given:
		Page page = new Page(title: 'Escort vessel')

		when:
		boolean shouldBeFilteredOut = spacecraftTypePageFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyOfCategories(page, Lists.newArrayList(CategoryTitle.SPACECRAFT_CLASSIFICATIONS)) >> false
		0 * _
		!shouldBeFilteredOut
	}

}
