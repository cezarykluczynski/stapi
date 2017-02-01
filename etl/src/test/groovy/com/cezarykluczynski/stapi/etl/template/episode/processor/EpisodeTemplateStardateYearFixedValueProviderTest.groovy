package com.cezarykluczynski.stapi.etl.template.episode.processor

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateYearDTO
import spock.lang.Specification

class EpisodeTemplateStardateYearFixedValueProviderTest extends Specification {

	private static final String EXISTING_TITLE = 'Year of Hell'
	private static final String NONEXISTING_TITLE = 'NONEXISTING_TITLE'

	private EpisodeTemplateStardateYearFixedValueProvider episodeStardateYearFixedValueProvider

	void setup() {
		episodeStardateYearFixedValueProvider = new EpisodeTemplateStardateYearFixedValueProvider()
	}

	void "gets fixed value holder when episode is found"() {
		when:
		FixedValueHolder<StardateYearDTO> stardateYearFixedValueHolder = episodeStardateYearFixedValueProvider
				.getSearchedValue(EXISTING_TITLE)

		then:
		stardateYearFixedValueHolder.found
		stardateYearFixedValueHolder.value != null
		stardateYearFixedValueHolder.value.stardateFrom == 51268.4F
		stardateYearFixedValueHolder.value.stardateTo == 51268.4F
		stardateYearFixedValueHolder.value.yearFrom == 2374
		stardateYearFixedValueHolder.value.yearTo == 2374
	}

	void "gets fixed value holder when episode is not found"() {
		when:
		FixedValueHolder<StardateYearDTO> stardateYearFixedValueHolder = episodeStardateYearFixedValueProvider
				.getSearchedValue(NONEXISTING_TITLE)

		then:
		!stardateYearFixedValueHolder.found
		stardateYearFixedValueHolder.value == null
	}

}
