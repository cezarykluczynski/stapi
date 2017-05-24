package com.cezarykluczynski.stapi.etl.series.creation.processor

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import spock.lang.Specification

class SeriesAbbreviationFixedValueProviderTest extends Specification {

	private SeriesAbbreviationFixedValueProvider seriesAbbreviationFixedValueProvider

	void setup() {
		seriesAbbreviationFixedValueProvider = new SeriesAbbreviationFixedValueProvider()
	}

	void "gets fixed value holder when series is found"() {
		when:
		FixedValueHolder<String> abbreviationFixedValueHolder = seriesAbbreviationFixedValueProvider.getSearchedValue('Star Trek: Voyager')

		then:
		abbreviationFixedValueHolder.found
		abbreviationFixedValueHolder.value == 'VOY'
	}

	void "gets fixed value holder when series is not found"() {
		when:
		FixedValueHolder<String> abbreviationFixedValueHolder = seriesAbbreviationFixedValueProvider.getSearchedValue('Star Trek: Deep Space Nine')

		then:
		!abbreviationFixedValueHolder.found
		abbreviationFixedValueHolder.value == null
	}

}
