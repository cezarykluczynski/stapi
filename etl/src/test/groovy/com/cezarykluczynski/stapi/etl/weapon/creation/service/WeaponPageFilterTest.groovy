package com.cezarykluczynski.stapi.etl.weapon.creation.service

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class WeaponPageFilterTest extends Specification {

	private WeaponPageFilter weaponPageFilter

	void setup() {
		weaponPageFilter = new WeaponPageFilter()
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
		Page page = new Page(title: WeaponPageFilter.NOT_WEAPONS[0])

		when:
		boolean shouldBeFilteredOut = weaponPageFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

	void "returns false for normal weapon"() {
		given:
		Page page = new Page(title: 'Covariant phaser pulse')

		when:
		boolean shouldBeFilteredOut = weaponPageFilter.shouldBeFilteredOut(page)

		then:
		!shouldBeFilteredOut
	}

}
