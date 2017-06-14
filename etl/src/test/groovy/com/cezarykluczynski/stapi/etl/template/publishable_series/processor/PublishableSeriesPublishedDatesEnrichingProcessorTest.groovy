package com.cezarykluczynski.stapi.etl.template.publishable_series.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.Range
import com.cezarykluczynski.stapi.etl.template.comic_series.dto.ComicSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import com.cezarykluczynski.stapi.etl.template.common.processor.DayMonthYearRangeProcessor
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class PublishableSeriesPublishedDatesEnrichingProcessorTest extends Specification {

	private DayMonthYearRangeProcessor dayMonthYearRangeProcessorMock

	private PublishableSeriesTemplateDayMonthYearRangeEnrichingProcessor publishableSeriesTemplateDayMonthYearRangeEnrichingProcessorMock

	private PublishableSeriesPublishedDatesEnrichingProcessor publishableSeriesPublishedDatesEnrichingProcessor

	void setup() {
		dayMonthYearRangeProcessorMock = Mock()
		publishableSeriesTemplateDayMonthYearRangeEnrichingProcessorMock = Mock()
		publishableSeriesPublishedDatesEnrichingProcessor = new PublishableSeriesPublishedDatesEnrichingProcessor(dayMonthYearRangeProcessorMock,
				publishableSeriesTemplateDayMonthYearRangeEnrichingProcessorMock)
	}

	void "when DayMonthYear range is not empty, it is not passed to enricher"() {
		given:
		Template.Part templatePart = Mock()
		ComicSeriesTemplate comicSeriesTemplate = Mock()
		DayMonthYear dayMonthYearFrom = Mock()
		DayMonthYear dayMonthYearTo = Mock()

		when:
		publishableSeriesPublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicSeriesTemplate))

		then:
		1 * dayMonthYearRangeProcessorMock.process(templatePart) >> Range.of(dayMonthYearFrom, dayMonthYearTo)
		1 * publishableSeriesTemplateDayMonthYearRangeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Pair<Template.Part, Range<DayMonthYear>>, ComicSeriesTemplate> enrichablePair ->
				assert enrichablePair.input.key == templatePart
				assert enrichablePair.input.value.from == dayMonthYearFrom
				assert enrichablePair.input.value.to == dayMonthYearTo
				assert enrichablePair.output == comicSeriesTemplate
		}
		0 * _
	}

	void "when DayMonthYear range is empty, it is not passed to enricher"() {
		given:
		Template.Part templatePart = Mock()
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()

		when:
		publishableSeriesPublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicSeriesTemplate))

		then:
		1 * dayMonthYearRangeProcessorMock.process(templatePart) >> Range.of(null, null)
		0 * _
	}

}
