package com.cezarykluczynski.stapi.etl.template.comicSeries.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.Range
import com.cezarykluczynski.stapi.etl.template.comicSeries.dto.ComicSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import com.cezarykluczynski.stapi.etl.template.common.processor.DayMonthYearRangeProcessor
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

class ComicSeriesPublishedDatesEnrichingProcessorTest extends Specification {

	private DayMonthYearRangeProcessor dayMonthYearRangeProcessorMock

	private ComicSeriesTemplateDayMonthYearRangeEnrichingProcessor comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock

	private ComicSeriesPublishedDatesEnrichingProcessor comicSeriesPublishedDatesEnrichingProcessor

	void setup() {
		dayMonthYearRangeProcessorMock = Mock()
		comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock = Mock()
		comicSeriesPublishedDatesEnrichingProcessor = new ComicSeriesPublishedDatesEnrichingProcessor(dayMonthYearRangeProcessorMock,
				comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock)
	}

	void "when DayMonthYear range is not empty, it is not passed to enricher"() {
		given:
		Template.Part templatePart = Mock()
		ComicSeriesTemplate comicSeriesTemplate = Mock()
		DayMonthYear dayMonthYearFrom = Mock()
		DayMonthYear dayMonthYearTo = Mock()

		when:
		comicSeriesPublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicSeriesTemplate))

		then:
		1 * dayMonthYearRangeProcessorMock.process(templatePart) >> Range.of(dayMonthYearFrom, dayMonthYearTo)
		1 * comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Range<DayMonthYear>, ComicSeriesTemplate> enrichablePair ->
			assert enrichablePair.input.from == dayMonthYearFrom
			assert enrichablePair.input.to == dayMonthYearTo
			assert enrichablePair.output == comicSeriesTemplate
		}
		0 * _
	}

	void "when DayMonthYear range is empty, it is not passed to enricher"() {
		given:
		Template.Part templatePart = Mock()
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()

		when:
		comicSeriesPublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicSeriesTemplate))

		then:
		1 * dayMonthYearRangeProcessorMock.process(templatePart) >> Range.of(null, null)
		0 * _
	}

}
