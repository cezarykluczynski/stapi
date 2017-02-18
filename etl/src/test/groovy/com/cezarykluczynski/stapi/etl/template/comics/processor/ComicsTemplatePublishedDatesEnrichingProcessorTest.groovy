package com.cezarykluczynski.stapi.etl.template.comics.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplateParameter
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

class ComicsTemplatePublishedDatesEnrichingProcessorTest extends Specification {

	private static final Integer YEAR = 1987
	private static final Integer MONTH = 10
	private static final Integer DAY = 19

	private ComicsTemplatePartToDayMonthRangeProcessor comicsTemplatePartToDayMonthRangeProcessorMock

	private ComicsTemplatePublishedDatesEnrichingProcessor comicsTemplatePublishedDatesEnrichingProcessor

	void setup () {
		comicsTemplatePartToDayMonthRangeProcessorMock = Mock(ComicsTemplatePartToDayMonthRangeProcessor)
		comicsTemplatePublishedDatesEnrichingProcessor = new ComicsTemplatePublishedDatesEnrichingProcessor(
				comicsTemplatePartToDayMonthRangeProcessorMock)
	}

	void "when DayMonthYear is null, ComicsTemplate is not interacted with"() {
		given:
		ComicsTemplate comicsTemplate = Mock(ComicsTemplate)
		Template.Part templatePart = new Template.Part()

		when:
		comicsTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicsTemplate))

		then:
		1 * comicsTemplatePartToDayMonthRangeProcessorMock.process(templatePart)
		0 * _
	}

	void "when DayMonthYear is found, and template part key contains published date, it is set to ComicsTemplate"() {
		given:
		DayMonthYear dayMonthYear = DayMonthYear.of(DAY, MONTH, YEAR)
		ComicsTemplate comicsTemplate = new ComicsTemplate()
		Template.Part templatePart = new Template.Part(key: ComicsTemplateParameter.PUBLISHED)

		when:
		comicsTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicsTemplate))

		then:
		1 * comicsTemplatePartToDayMonthRangeProcessorMock.process(templatePart) >> dayMonthYear
		0 * _
		comicsTemplate.publishedYear == YEAR
		comicsTemplate.publishedMonth == MONTH
		comicsTemplate.publishedDay == DAY
		comicsTemplate.coverYear == null
		comicsTemplate.coverMonth == null
		comicsTemplate.coverDay == null
	}

	void "when DayMonthYear is found, and template part key contains cover date, it is set to ComicsTemplate"() {
		given:
		DayMonthYear dayMonthYear = DayMonthYear.of(DAY, MONTH, YEAR)
		ComicsTemplate comicsTemplate = new ComicsTemplate()
		Template.Part templatePart = new Template.Part(key: ComicsTemplateParameter.COVER_DATE)

		when:
		comicsTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicsTemplate))

		then:
		1 * comicsTemplatePartToDayMonthRangeProcessorMock.process(templatePart) >> dayMonthYear
		0 * _
		comicsTemplate.coverYear == YEAR
		comicsTemplate.coverMonth == MONTH
		comicsTemplate.coverDay == DAY
		comicsTemplate.publishedYear == null
		comicsTemplate.publishedMonth == null
		comicsTemplate.publishedDay == null
	}

	void "when DayMonthYear is found, and template part key contains unrecognized key, ComicsTemplate is not interacted with"() {
		given:
		DayMonthYear dayMonthYear = DayMonthYear.of(DAY, MONTH, YEAR)
		ComicsTemplate comicsTemplate = Mock(ComicsTemplate)
		Template.Part templatePart = new Template.Part(key: 'UNKNOWN')

		when:
		comicsTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicsTemplate))

		then:
		1 * comicsTemplatePartToDayMonthRangeProcessorMock.process(templatePart) >> dayMonthYear
		0 * _
	}

}
