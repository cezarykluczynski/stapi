package com.cezarykluczynski.stapi.etl.template.movie.processor

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateYearDTO
import spock.lang.Specification

class MovieTemplateStardateYearFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'Star Trek'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private MovieTemplateStardateYearFixedValueProvider movieTemplateStardateYearFixedValueProvider

	void setup() {
		movieTemplateStardateYearFixedValueProvider = new MovieTemplateStardateYearFixedValueProvider()
	}

	void "gets fixed value holder when movie is found"() {
		when:
		FixedValueHolder<StardateYearDTO> stardateYearFixedValueHolder = movieTemplateStardateYearFixedValueProvider
				.getSearchedValue(EXISTING_TITLE)

		then:
		stardateYearFixedValueHolder.found
		stardateYearFixedValueHolder.value != null
		stardateYearFixedValueHolder.value.stardateFrom == 2233.04F
		stardateYearFixedValueHolder.value.stardateTo == 2258.42F
		stardateYearFixedValueHolder.value.yearFrom == 2233
		stardateYearFixedValueHolder.value.yearTo == 2387
	}

	void "gets fixed value holder when movie is not found"() {
		when:
		FixedValueHolder<StardateYearDTO> stardateYearFixedValueHolder = movieTemplateStardateYearFixedValueProvider
				.getSearchedValue(NONEXISTING_TITLE)

		then:
		!stardateYearFixedValueHolder.found
		stardateYearFixedValueHolder.value == null
	}

}
