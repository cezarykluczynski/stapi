package com.cezarykluczynski.stapi.etl.series.creation.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DateRange
import com.cezarykluczynski.stapi.etl.template.series.dto.SeriesTemplate
import spock.lang.Specification

import java.time.LocalDate

class SeriesRunDateEnrichingProcessorTest extends Specification {

	static final String ABBREVIATION = 'AT'
	static final LocalDate START_DATE = LocalDate.of(1996, 1, 1)
	static final LocalDate END_DATE = LocalDate.of(1999, 2, 2)
	static final DateRange DATE_RANGE = DateRange.of(START_DATE, END_DATE)

	SeriesRunDateFixedValueProvider seriesRunDateFixedValueProviderMock
	SeriesRunDateEnrichingProcessor seriesRunDateEnrichingProcessor

	void setup() {
		seriesRunDateFixedValueProviderMock = Mock()
		seriesRunDateEnrichingProcessor = new SeriesRunDateEnrichingProcessor(seriesRunDateFixedValueProviderMock)
	}

	void "sets run start date from SeriesRunDateFixedValueProvider"() {
		given:
		SeriesTemplate seriesTemplate = new SeriesTemplate(abbreviation: ABBREVIATION, originalRunDateRange: new DateRange())
		EnrichablePair<SeriesTemplate, SeriesTemplate> seriesTemplateEnrichablePair = EnrichablePair.ofSingle(seriesTemplate)

		when:
		seriesRunDateEnrichingProcessor.enrich(seriesTemplateEnrichablePair)

		then:
		1 * seriesRunDateFixedValueProviderMock.getSearchedValue(ABBREVIATION) >> FixedValueHolder.found(DateRange.of(START_DATE, null))
		0 * _
		seriesTemplate.originalRunDateRange.startDate == START_DATE
		seriesTemplate.originalRunDateRange.endDate == null
	}

	void "sets run end date from SeriesRunDateFixedValueProvider"() {
		given:
		SeriesTemplate seriesTemplate = new SeriesTemplate(abbreviation: ABBREVIATION, originalRunDateRange: new DateRange())
		EnrichablePair<SeriesTemplate, SeriesTemplate> seriesTemplateEnrichablePair = EnrichablePair.ofSingle(seriesTemplate)

		when:
		seriesRunDateEnrichingProcessor.enrich(seriesTemplateEnrichablePair)

		then:
		1 * seriesRunDateFixedValueProviderMock.getSearchedValue(ABBREVIATION) >> FixedValueHolder.found(DateRange.of(null, END_DATE))
		0 * _
		seriesTemplate.originalRunDateRange.startDate == null
		seriesTemplate.originalRunDateRange.endDate == END_DATE
	}

	void "does nothing when fixed value is not found"() {
		given:
		SeriesTemplate seriesTemplate = new SeriesTemplate(abbreviation: ABBREVIATION, originalRunDateRange: new DateRange())
		EnrichablePair<SeriesTemplate, SeriesTemplate> seriesTemplateEnrichablePair = EnrichablePair.ofSingle(seriesTemplate)

		when:
		seriesRunDateEnrichingProcessor.enrich(seriesTemplateEnrichablePair)

		then:
		1 * seriesRunDateFixedValueProviderMock.getSearchedValue(ABBREVIATION) >> FixedValueHolder.notFound()
		0 * _
		seriesTemplate.originalRunDateRange.startDate == null
		seriesTemplate.originalRunDateRange.endDate == null
	}

	void "does nothing when run start date and end date is already set"() {
		given:
		SeriesTemplate seriesTemplate = new SeriesTemplate(abbreviation: ABBREVIATION, originalRunDateRange: DATE_RANGE)
		EnrichablePair<SeriesTemplate, SeriesTemplate> seriesTemplateEnrichablePair = EnrichablePair.ofSingle(seriesTemplate)

		when:
		seriesRunDateEnrichingProcessor.enrich(seriesTemplateEnrichablePair)

		then:
		1 * seriesRunDateFixedValueProviderMock.getSearchedValue(ABBREVIATION) >> FixedValueHolder.found(DateRange.of(null, null))
		0 * _
		seriesTemplate.originalRunDateRange.startDate == START_DATE
		seriesTemplate.originalRunDateRange.endDate == END_DATE
	}

}
