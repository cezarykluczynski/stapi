package com.cezarykluczynski.stapi.etl.template.episode.provider

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import spock.lang.Specification

import java.time.LocalDate

class EpisodeFinalScriptDateFixedValueProviderTest extends Specification {

	private static final String FIXED_TITLE = 'Albatross'

	private EpisodeFinalScriptDateFixedValueProvider episodeScriptDateFixedValueProvider

	def setup() {
		episodeScriptDateFixedValueProvider = new EpisodeFinalScriptDateFixedValueProvider()
	}

	def "gets fixed date when it is present"() {
		when:
		FixedValueHolder<LocalDate> fixedValueHolder = episodeScriptDateFixedValueProvider.getSearchedValue(FIXED_TITLE)

		then:
		fixedValueHolder.found
		fixedValueHolder.value == EpisodeFinalScriptDateFixedValueProvider.FIXED_DATES.get(FIXED_TITLE)
	}

}
