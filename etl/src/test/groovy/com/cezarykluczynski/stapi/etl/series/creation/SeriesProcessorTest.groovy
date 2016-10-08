package com.cezarykluczynski.stapi.etl.series.creation

import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.wiki.dto.PageHeader
import spock.lang.Specification

class SeriesProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'

	private SeriesProcessor seriesProcessor

	def setup() {
		seriesProcessor = new SeriesProcessor()
	}

	def "converts PageHeader to Series"() {
		given:
		PageHeader pageHeader = PageHeader.builder().title(TITLE).build()

		when:
		Series series = seriesProcessor.process(pageHeader)

		then:
		series.title == TITLE
	}

}
