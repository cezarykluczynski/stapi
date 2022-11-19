package com.cezarykluczynski.stapi.etl.template.magazine_series.service

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class MagazineSeriesPageFilterTest extends Specification {

	MagazineSeriesPageFilter magazineSeriesPageFilter

	void setup() {
		magazineSeriesPageFilter = new MagazineSeriesPageFilter()
	}

	void "returns true when page title is among invalid page titles"() {
		given:
		Page page = new Page(title: RandomUtil.randomItem(MagazineSeriesPageFilter.INVALID_TITLES))

		when:
		boolean shouldBeFilteredOut = magazineSeriesPageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns true when page is a product of redirect"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(redirectPath: Lists.newArrayList(pageHeader))

		when:
		boolean shouldBeFilteredOut = magazineSeriesPageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns false when everything is in order"() {
		given:
		Page page = new Page(redirectPath: [])

		when:
		boolean shouldBeFilteredOut = magazineSeriesPageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		!shouldBeFilteredOut
	}

}
