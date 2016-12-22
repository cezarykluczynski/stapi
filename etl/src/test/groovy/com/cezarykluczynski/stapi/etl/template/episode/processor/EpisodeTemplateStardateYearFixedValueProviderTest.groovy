package com.cezarykluczynski.stapi.etl.template.episode.processor

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.template.episode.dto.StardateYearDTO
import spock.lang.Specification

class EpisodeTemplateStardateYearFixedValueProviderTest extends Specification {

	private EpisodeTemplateStardateYearFixedValueProvider episodeStardateYearFixedValueProvider

	def setup() {
		episodeStardateYearFixedValueProvider = new EpisodeTemplateStardateYearFixedValueProvider()
	}

	def "gets fixed value holder when episode is found"() {
		when:
		FixedValueHolder<StardateYearDTO> stardateYearFixedValueHolder = episodeStardateYearFixedValueProvider
				.getSearchedValue('Year of Hell')

		then:
		stardateYearFixedValueHolder.found
		stardateYearFixedValueHolder.value != null
		stardateYearFixedValueHolder.value.stardateFrom == 51268.4F
		stardateYearFixedValueHolder.value.stardateTo == 51268.4F
		stardateYearFixedValueHolder.value.yearFrom == 2374
		stardateYearFixedValueHolder.value.yearTo == 2374
	}

	def "gets fixed value holder when episode is not found"() {
		when:
		FixedValueHolder<StardateYearDTO> stardateYearFixedValueHolder = episodeStardateYearFixedValueProvider
				.getSearchedValue('Not a title')

		then:
		!stardateYearFixedValueHolder.found
		stardateYearFixedValueHolder.value == null
	}

}
