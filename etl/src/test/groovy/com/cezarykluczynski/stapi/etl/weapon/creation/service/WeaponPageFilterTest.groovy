package com.cezarykluczynski.stapi.etl.weapon.creation.service

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class WeaponPageFilterTest extends Specification {

	private CategorySortingService categorySortingServiceMock

	private WeaponPageFilter weaponPageFilter

	void setup() {
		categorySortingServiceMock = Mock()
		weaponPageFilter = new WeaponPageFilter(categorySortingServiceMock)
	}

	void "returns true when redirect path is not empty"() {
		given:
		PageHeader pageHeaderRedirect = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeaderRedirect))

		when:
		boolean shouldBeFilteredOut = weaponPageFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

	void "returns true when page title is on list of title to filter out"() {
		given:
		Page page = new Page(title: WeaponPageFilter.INVALID_TITLES[0])

		when:
		boolean shouldBeFilteredOut = weaponPageFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

	void "returns true when page is filtered on top of 'Weapons' category"() {
		given:
		Page page = new Page(title: 'Torpedo')

		when:
		boolean shouldBeFilteredOut = weaponPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyOfCategories(page, [CategoryTitle.WEAPONS]) >> true
		shouldBeFilteredOut
	}

	void "returns false for normal weapon"() {
		given:
		Page page = new Page(title: 'Covariant phaser pulse')

		when:
		boolean shouldBeFilteredOut = weaponPageFilter.shouldBeFilteredOut(page)

		then:
		1 * categorySortingServiceMock.isSortedOnTopOfAnyOfCategories(page, [CategoryTitle.WEAPONS]) >> false
		!shouldBeFilteredOut
	}

}
