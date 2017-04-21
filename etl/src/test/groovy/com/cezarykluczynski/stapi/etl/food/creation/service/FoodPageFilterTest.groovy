package com.cezarykluczynski.stapi.etl.food.creation.service

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class FoodPageFilterTest extends Specification {

	private FoodPageFilter foodPageFilter

	void setup() {
		foodPageFilter = new FoodPageFilter()
	}

	void "returns true when redirect path is not empty"() {
		given:
		PageHeader pageHeaderRedirect = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeaderRedirect))

		when:
		boolean shouldBeFilteredOut = foodPageFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

	void "returns true when page title starts with 'Unnnamed'"() {
		given:
		Page page = new Page(title: 'Unnamed drinks (23rd century)')

		when:
		boolean shouldBeFilteredOut = foodPageFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

	void "returns true when page title is on list of title to filter out"() {
		given:
		Page page = new Page(title: FoodPageFilter.NOT_FOODS[0])

		when:
		boolean shouldBeFilteredOut = foodPageFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

	void "returns false for normal food"() {
		given:
		Page page = new Page(title: 'Earl Grey tea')

		when:
		boolean shouldBeFilteredOut = foodPageFilter.shouldBeFilteredOut(page)

		then:
		!shouldBeFilteredOut
	}

}
