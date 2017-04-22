package com.cezarykluczynski.stapi.etl.template.book.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplate
import com.cezarykluczynski.stapi.etl.template.book.dto.BookTemplateParameter
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatePartToDayMonthYearProcessor
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

class BookTemplatePublishedDatesEnrichingProcessorTest extends Specification {

	private static final Integer YEAR = 1987
	private static final Integer MONTH = 10
	private static final Integer DAY = 19

	private DatePartToDayMonthYearProcessor bookTemplatePartToDayMonthRangeProcessorMock

	private BookPublishedDateFixedValueProvider bookPublishedDateFixedValueProviderMock

	private BookTemplatePublishedDatesEnrichingProcessor bookTemplatePublishedDatesEnrichingProcessor

	void setup () {
		bookTemplatePartToDayMonthRangeProcessorMock = Mock()
		bookPublishedDateFixedValueProviderMock = Mock()
		bookTemplatePublishedDatesEnrichingProcessor = new BookTemplatePublishedDatesEnrichingProcessor(bookTemplatePartToDayMonthRangeProcessorMock,
				bookPublishedDateFixedValueProviderMock)
	}

	void "when BookPublishedDateFixedValueProvider finds value, it is used and no other interactions are performed"() {
		given:
		DayMonthYear dayMonthYear = new DayMonthYear(DAY, MONTH, YEAR)
		BookTemplate bookTemplate = new BookTemplate()
		Template.Part templatePart = new Template.Part()

		when:
		bookTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, bookTemplate))

		then:
		1 * bookPublishedDateFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.found(dayMonthYear)
		0 * _
		bookTemplate.publishedDay == DAY
		bookTemplate.publishedMonth == MONTH
		bookTemplate.publishedYear == YEAR
	}

	void "when DayMonthYear is null, BookTemplate is not interacted with"() {
		given:
		BookTemplate bookTemplate = new BookTemplate()
		Template.Part templatePart = new Template.Part()

		when:
		bookTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, bookTemplate))

		then:
		1 * bookPublishedDateFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * bookTemplatePartToDayMonthRangeProcessorMock.process(templatePart)
		0 * _
	}

	void "when DayMonthYear is found, and template part key contains published date, it is set to BookTemplate"() {
		given:
		DayMonthYear dayMonthYear = DayMonthYear.of(DAY, MONTH, YEAR)
		BookTemplate bookTemplate = new BookTemplate()
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.PUBLISHED)

		when:
		bookTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, bookTemplate))

		then:
		1 * bookPublishedDateFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * bookTemplatePartToDayMonthRangeProcessorMock.process(templatePart) >> dayMonthYear
		0 * _
		bookTemplate.publishedYear == YEAR
		bookTemplate.publishedMonth == MONTH
		bookTemplate.publishedDay == DAY
		bookTemplate.audiobookPublishedYear == null
		bookTemplate.audiobookPublishedMonth == null
		bookTemplate.audiobookPublishedDay == null
	}

	void "when DayMonthYear is found, and template part key contains cover date, it is set to BookTemplate"() {
		given:
		DayMonthYear dayMonthYear = DayMonthYear.of(DAY, MONTH, YEAR)
		BookTemplate bookTemplate = new BookTemplate()
		Template.Part templatePart = new Template.Part(key: BookTemplateParameter.AUDIOBOOK_PUBLISHED)

		when:
		bookTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, bookTemplate))

		then:
		1 * bookPublishedDateFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * bookTemplatePartToDayMonthRangeProcessorMock.process(templatePart) >> dayMonthYear
		0 * _
		bookTemplate.audiobookPublishedYear == YEAR
		bookTemplate.audiobookPublishedMonth == MONTH
		bookTemplate.audiobookPublishedDay == DAY
		bookTemplate.publishedYear == null
		bookTemplate.publishedMonth == null
		bookTemplate.publishedDay == null
	}

	void "when DayMonthYear is found, and template part key contains unrecognized key, BookTemplate is not interacted with"() {
		given:
		DayMonthYear dayMonthYear = DayMonthYear.of(DAY, MONTH, YEAR)
		BookTemplate bookTemplate = new BookTemplate()
		Template.Part templatePart = new Template.Part(key: 'UNKNOWN')

		when:
		bookTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, bookTemplate))

		then:
		1 * bookPublishedDateFixedValueProviderMock.getSearchedValue(_) >> FixedValueHolder.empty()
		1 * bookTemplatePartToDayMonthRangeProcessorMock.process(templatePart) >> dayMonthYear
		0 * _
	}

}
