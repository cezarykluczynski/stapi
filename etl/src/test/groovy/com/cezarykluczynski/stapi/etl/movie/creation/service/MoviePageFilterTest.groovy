package com.cezarykluczynski.stapi.etl.movie.creation.service

import com.cezarykluczynski.stapi.etl.common.service.CategorySortingService
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import spock.lang.Specification

class MoviePageFilterTest extends Specification {

	private CategorySortingService categorySortingServiceMock

	private MoviePageFilter moviePageFilter

	void setup() {
		categorySortingServiceMock = Mock()
		moviePageFilter = new MoviePageFilter()
	}

	void "returns true when title is among invalid titles"() {
		given:
		Page page = new Page(title: RandomUtil.randomItem(MoviePageFilter.INVALID_TITLES))

		when:
		boolean shouldBeFilteredOut = moviePageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		shouldBeFilteredOut
	}

	void "returns false when title is not among invalid titles"() {
		given:
		Page page = new Page(title: 'Star Trek: Insurrection')

		when:
		boolean shouldBeFilteredOut = moviePageFilter.shouldBeFilteredOut(page)

		then:
		0 * _
		!shouldBeFilteredOut
	}

}
