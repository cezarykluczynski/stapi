package com.cezarykluczynski.stapi.etl.book_series.creation.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.common.dto.Range
import com.cezarykluczynski.stapi.etl.template.book_series.dto.BookSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.book_series.processor.BookSeriesPublishedDateFixedValueProvider
import com.cezarykluczynski.stapi.etl.template.book_series.processor.BookSeriesTemplateFixedValuesEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.book_series.processor.BookSeriesTemplateNumberOfBooksFixedValueProvider
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import com.cezarykluczynski.stapi.etl.template.publishable_series.processor.PublishableSeriesTemplateDayMonthYearRangeEnrichingProcessor
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class BookSeriesTemplateFixedValuesEnrichingProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final Integer NUMBER_OF_BOOKS = 14

	private BookSeriesPublishedDateFixedValueProvider bookSeriesPublishedDateFixedValueProviderMock

	private PublishableSeriesTemplateDayMonthYearRangeEnrichingProcessor publishableSeriesTemplateDayMonthYearRangeEnrichingProcessorMock

	private BookSeriesTemplateNumberOfBooksFixedValueProvider bookSeriesTemplateNumberOfBooksFixedValueProviderMock

	private BookSeriesTemplateFixedValuesEnrichingProcessor bookSeriesTemplateFixedValuesEnrichingProcessor

	void setup() {
		bookSeriesPublishedDateFixedValueProviderMock = Mock()
		publishableSeriesTemplateDayMonthYearRangeEnrichingProcessorMock = Mock()
		bookSeriesTemplateNumberOfBooksFixedValueProviderMock = Mock()
		bookSeriesTemplateFixedValuesEnrichingProcessor = new BookSeriesTemplateFixedValuesEnrichingProcessor(
				bookSeriesPublishedDateFixedValueProviderMock, publishableSeriesTemplateDayMonthYearRangeEnrichingProcessorMock,
				bookSeriesTemplateNumberOfBooksFixedValueProviderMock)
	}

	void "when fixed value for published dates is found, it is passed to enriching processor"() {
		given:
		BookSeriesTemplate bookSeriesTemplate = new BookSeriesTemplate(title: TITLE)
		Range<DayMonthYear> dayMonthYearRange = Mock()

		when:
		bookSeriesTemplateFixedValuesEnrichingProcessor.enrich(EnrichablePair.of(bookSeriesTemplate, bookSeriesTemplate))

		then:
		1 * bookSeriesPublishedDateFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.found(dayMonthYearRange)
		1 * publishableSeriesTemplateDayMonthYearRangeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Pair<Template.Part, Range<DayMonthYear>>, BookSeriesTemplate> enrichablePair ->
				assert enrichablePair.input.key == null
				assert enrichablePair.input.value == dayMonthYearRange
				assert enrichablePair.output != null
		}
		1 * bookSeriesTemplateNumberOfBooksFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.empty()
		0 * _
		bookSeriesTemplate.title == TITLE
	}

	void "when fixed value for published dates is found, it is set to entity"() {
		given:
		BookSeriesTemplate bookSeriesTemplate = new BookSeriesTemplate(title: TITLE)

		when:
		bookSeriesTemplateFixedValuesEnrichingProcessor.enrich(EnrichablePair.of(bookSeriesTemplate, bookSeriesTemplate))

		then:
		1 * bookSeriesPublishedDateFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.empty()
		1 * bookSeriesTemplateNumberOfBooksFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.found(NUMBER_OF_BOOKS)
		0 * _
		bookSeriesTemplate.numberOfBooks == NUMBER_OF_BOOKS
	}

}
