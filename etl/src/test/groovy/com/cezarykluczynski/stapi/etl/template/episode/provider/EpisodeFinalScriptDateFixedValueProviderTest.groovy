package com.cezarykluczynski.stapi.etl.template.episode.provider

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import spock.lang.Specification

import java.time.LocalDate

class EpisodeFinalScriptDateFixedValueProviderTest extends Specification {

	private static final String FIXED_TITLE = 'Albatross'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private EpisodeFinalScriptDateFixedValueProvider episodeScriptDateFixedValueProvider

	void setup() {
		episodeScriptDateFixedValueProvider = new EpisodeFinalScriptDateFixedValueProvider()
	}

	void "gets fixed value holder when episode is found"() {
		when:
		FixedValueHolder<LocalDate> fixedValueHolder = episodeScriptDateFixedValueProvider.getSearchedValue(FIXED_TITLE)

		then:
		fixedValueHolder.found
		fixedValueHolder.value == EpisodeFinalScriptDateFixedValueProvider.FIXED_DATES.get(FIXED_TITLE)
	}

	void "gets fixed value holder when episode is not found"() {
		when:
		FixedValueHolder<LocalDate> fixedValueHolder = episodeScriptDateFixedValueProvider
				.getSearchedValue(NONEXISTING_TITLE)

		then:
		!fixedValueHolder.found
		fixedValueHolder.value == null
	}

}
