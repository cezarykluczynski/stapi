package com.cezarykluczynski.stapi.etl.soundtrack.creation.service

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService
import com.cezarykluczynski.stapi.etl.template.soundtrack.service.SoundtrackFilter
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class SoundtrackFilterTest extends Specification {

	private CategorySortingService categorySortingServiceMock

	private SoundtrackFilter soundtrackFilter

	void setup() {
		categorySortingServiceMock = Mock()
		soundtrackFilter = new SoundtrackFilter(categorySortingServiceMock)
	}

	void "returns true when page is sorted on top of 'Soundtracks' category"() {
		given:
		Page page = Mock()

		when:
		boolean shouldBeFilteredOut = soundtrackFilter.shouldBeFilteredOut(page)

		then:
		categorySortingServiceMock.isSortedOnTopOfAnyOfCategories(page, Lists.newArrayList(CategoryTitle.SOUNDTRACKS)) >> true
		shouldBeFilteredOut
	}

	void "returns false when page is not sorted on top of 'Soundtracks' category"() {
		given:
		Page page = Mock()

		when:
		boolean shouldBeFilteredOut = soundtrackFilter.shouldBeFilteredOut(page)

		then:
		categorySortingServiceMock.isSortedOnTopOfAnyOfCategories(page, Lists.newArrayList(CategoryTitle.SOUNDTRACKS)) >> false
		!shouldBeFilteredOut
	}

}
