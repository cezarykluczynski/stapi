package com.cezarykluczynski.stapi.etl.template.publishable.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.book.dto.ReferenceBookTemplateParameter
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplateParameter
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatePartToDayMonthYearProcessor
import com.cezarykluczynski.stapi.etl.template.magazine.dto.MagazineTemplate
import com.cezarykluczynski.stapi.etl.template.magazine.dto.MagazineTemplateParameter
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

class PublishableTemplatePublishedDatesEnrichingProcessorTest extends Specification {

	private static final Integer YEAR = 1987
	private static final Integer MONTH = 10
	private static final Integer DAY = 19

	private DatePartToDayMonthYearProcessor datePartToDayMonthYearProcessorMock

	private PublishableTemplatePublishedDatesEnrichingProcessor publishableTemplatePublishedDatesEnrichingProcessor

	void setup () {
		datePartToDayMonthYearProcessorMock = Mock()
		publishableTemplatePublishedDatesEnrichingProcessor = new PublishableTemplatePublishedDatesEnrichingProcessor(
				datePartToDayMonthYearProcessorMock)
	}

	void "when DayMonthYear is null, ComicsTemplate is not interacted with"() {
		given:
		ComicsTemplate comicsTemplate = Mock()
		Template.Part templatePart = new Template.Part()

		when:
		publishableTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicsTemplate))

		then:
		1 * datePartToDayMonthYearProcessorMock.process(templatePart)
		0 * _
	}

	void "when DayMonthYear is found, and template part key contains comics published date, it is set to PublishableTemplate"() {
		given:
		DayMonthYear dayMonthYear = DayMonthYear.of(DAY, MONTH, YEAR)
		ComicsTemplate comicsTemplate = new ComicsTemplate()
		Template.Part templatePart = new Template.Part(key: ComicsTemplateParameter.PUBLISHED)

		when:
		publishableTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicsTemplate))

		then:
		1 * datePartToDayMonthYearProcessorMock.process(templatePart) >> dayMonthYear
		0 * _
		comicsTemplate.publishedYear == YEAR
		comicsTemplate.publishedMonth == MONTH
		comicsTemplate.publishedDay == DAY
		comicsTemplate.coverYear == null
		comicsTemplate.coverMonth == null
		comicsTemplate.coverDay == null
	}

	void "when DayMonthYear is found, and template part key contains reference book published date, it is set to PublishableTemplate"() {
		given:
		DayMonthYear dayMonthYear = DayMonthYear.of(DAY, MONTH, YEAR)
		ComicsTemplate comicsTemplate = new ComicsTemplate()
		Template.Part templatePart = new Template.Part(key: ReferenceBookTemplateParameter.PUBLISHED)

		when:
		publishableTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicsTemplate))

		then:
		1 * datePartToDayMonthYearProcessorMock.process(templatePart) >> dayMonthYear
		0 * _
		comicsTemplate.publishedYear == YEAR
		comicsTemplate.publishedMonth == MONTH
		comicsTemplate.publishedDay == DAY
		comicsTemplate.coverYear == null
		comicsTemplate.coverMonth == null
		comicsTemplate.coverDay == null
	}

	void "when DayMonthYear is found, and template part key contains magazine release date, it is set to PublishableTemplate"() {
		given:
		DayMonthYear dayMonthYear = DayMonthYear.of(DAY, MONTH, YEAR)
		MagazineTemplate magazineTemplate = new MagazineTemplate()
		Template.Part templatePart = new Template.Part(key: MagazineTemplateParameter.RELEASED)

		when:
		publishableTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, magazineTemplate))

		then:
		1 * datePartToDayMonthYearProcessorMock.process(templatePart) >> dayMonthYear
		0 * _
		magazineTemplate.publishedYear == YEAR
		magazineTemplate.publishedMonth == MONTH
		magazineTemplate.publishedDay == DAY
		magazineTemplate.coverYear == null
		magazineTemplate.coverMonth == null
		magazineTemplate.coverDay == null
	}

	void "when DayMonthYear is found, and template part key contains comics cover date, it is set to PublishableTemplate"() {
		given:
		DayMonthYear dayMonthYear = DayMonthYear.of(DAY, MONTH, YEAR)
		ComicsTemplate comicsTemplate = new ComicsTemplate()
		Template.Part templatePart = new Template.Part(key: ComicsTemplateParameter.COVER_DATE)

		when:
		publishableTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicsTemplate))

		then:
		1 * datePartToDayMonthYearProcessorMock.process(templatePart) >> dayMonthYear
		0 * _
		comicsTemplate.coverYear == YEAR
		comicsTemplate.coverMonth == MONTH
		comicsTemplate.coverDay == DAY
		comicsTemplate.publishedYear == null
		comicsTemplate.publishedMonth == null
		comicsTemplate.publishedDay == null
	}

	void "when DayMonthYear is found, and template part key contains magazine cover date, it is set to PublishableTemplate"() {
		given:
		DayMonthYear dayMonthYear = DayMonthYear.of(DAY, MONTH, YEAR)
		MagazineTemplate magazineTemplate = new MagazineTemplate()
		Template.Part templatePart = new Template.Part(key: MagazineTemplateParameter.COVER_DATE)

		when:
		publishableTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, magazineTemplate))

		then:
		1 * datePartToDayMonthYearProcessorMock.process(templatePart) >> dayMonthYear
		0 * _
		magazineTemplate.coverYear == YEAR
		magazineTemplate.coverMonth == MONTH
		magazineTemplate.coverDay == DAY
		magazineTemplate.publishedYear == null
		magazineTemplate.publishedMonth == null
		magazineTemplate.publishedDay == null
	}

	void "when DayMonthYear is found, and template part key contains unrecognized key, PublishableTemplate is not interacted with"() {
		given:
		DayMonthYear dayMonthYear = DayMonthYear.of(DAY, MONTH, YEAR)
		ComicsTemplate comicsTemplate = Mock()
		Template.Part templatePart = new Template.Part(key: 'UNKNOWN')

		when:
		publishableTemplatePublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicsTemplate))

		then:
		1 * datePartToDayMonthYearProcessorMock.process(templatePart) >> dayMonthYear
		0 * _
	}

}
