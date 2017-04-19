package com.cezarykluczynski.stapi.etl.template.bookSeries.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.company.WikitextToCompaniesProcessor
import com.cezarykluczynski.stapi.etl.template.bookSeries.dto.BookSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.bookSeries.dto.BookSeriesTemplateParameter
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplateParameter
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateRange
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PublishableSeriesPublishedDatesEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PublishableSeriesTemplateMiniseriesProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToStardateRangeProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToYearRangeProcessor
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.tool.LogicUtil
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class BookSeriesTemplatePartsEnrichingProcessorTest extends Specification {

	private static final String PUBLISHER = 'PUBLISHER'
	private static final String PUBLISHED = 'PUBLISHED'
	private static final String YEARS = '1995-1997'
	private static final Integer YEAR = 1995
	private static final Integer YEAR_FROM = 1995
	private static final Integer YEAR_TO = 1997
	private static final String STARDATES = '1995-1997'
	private static final Float STARDATE_FROM = 123.4F
	private static final Float STARDATE_TO = 456.7F
	private static final String NOVELS_STRING = '12'
	private static final Integer NOVELS_INTEGER = 12
	private static final Integer NOVELS_INTEGER_2 = 22
	private static final String SERIES = 'SERIES'
	private static final Boolean MINISERIES = LogicUtil.nextBoolean()

	private WikitextToCompaniesProcessor wikitextToCompaniesProcessorMock

	private PublishableSeriesPublishedDatesEnrichingProcessor bookSeriesPublishedDatesEnrichingProcessorMock

	private WikitextToYearRangeProcessor wikitextToYearRangeProcessorMock

	private WikitextToStardateRangeProcessor wikitextToStardateRangeProcessorMock

	private PublishableSeriesTemplateMiniseriesProcessor bookSeriesTemplateMiniseriesProcessorMock

	private BookSeriesTemplatePartsEnrichingProcessor bookSeriesTemplatePartsEnrichingProcessor

	void setup() {
		wikitextToCompaniesProcessorMock = Mock()
		bookSeriesPublishedDatesEnrichingProcessorMock = Mock()
		wikitextToYearRangeProcessorMock = Mock()
		wikitextToStardateRangeProcessorMock = Mock()
		bookSeriesTemplateMiniseriesProcessorMock = Mock()
		bookSeriesTemplatePartsEnrichingProcessor = new BookSeriesTemplatePartsEnrichingProcessor(wikitextToCompaniesProcessorMock,
				bookSeriesPublishedDatesEnrichingProcessorMock, wikitextToYearRangeProcessorMock, wikitextToStardateRangeProcessorMock,
				bookSeriesTemplateMiniseriesProcessorMock)
	}

	void "sets publishers from BookSeriesTemplatePublishersProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookSeriesTemplateParameter.PUBLISHER, value: PUBLISHER)
		BookSeriesTemplate bookSeriesTemplate = new BookSeriesTemplate()
		Company publisher1 = Mock(Company)
		Company publisher2 = Mock(Company)

		when:
		bookSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookSeriesTemplate))

		then:
		1 * wikitextToCompaniesProcessorMock.process(PUBLISHER) >> Sets.newHashSet(publisher1, publisher2)
		0 * _
		bookSeriesTemplate.publishers.size() == 2
		bookSeriesTemplate.publishers.contains publisher1
		bookSeriesTemplate.publishers.contains publisher2
	}

	void "passes BookSeriesTemplate to BookSeriesPublishedDatesEnrichingProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookSeriesTemplateParameter.PUBLISHED, value: PUBLISHED)
		BookSeriesTemplate bookSeriesTemplate = new BookSeriesTemplate()

		when:
		bookSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookSeriesTemplate))

		then:
		1 * bookSeriesPublishedDatesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template.Part, BookSeriesTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output != null
		}
		0 * _
	}

	void "does not pass BookSeriesTemplate to BookSeriesPublishedDatesEnrichingProcessor when is already have published years"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookSeriesTemplateParameter.PUBLISHED, value: PUBLISHED)
		BookSeriesTemplate bookSeriesTemplateWithPublishedYearFrom = new BookSeriesTemplate(publishedYearFrom: YEAR)
		BookSeriesTemplate bookSeriesTemplateWithPublishedYearTo = new BookSeriesTemplate(publishedYearTo: YEAR)

		when:
		bookSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart),
				bookSeriesTemplateWithPublishedYearFrom))

		then:
		0 * _

		when:
		bookSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart),
				bookSeriesTemplateWithPublishedYearTo))

		then:
		0 * _
	}

	void "parses number of issues"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookSeriesTemplateParameter.NOVELS, value: NOVELS_STRING)
		BookSeriesTemplate bookSeriesTemplate = new BookSeriesTemplate()

		when:
		bookSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookSeriesTemplate))

		then:
		0 * _
		bookSeriesTemplate.numberOfBooks == NOVELS_INTEGER
	}

	void "does not set number of issues, when value is already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookSeriesTemplateParameter.NOVELS, value: NOVELS_STRING)
		BookSeriesTemplate bookSeriesTemplate = new BookSeriesTemplate(numberOfBooks: NOVELS_INTEGER_2)

		when:
		bookSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookSeriesTemplate))

		then:
		0 * _
		bookSeriesTemplate.numberOfBooks == NOVELS_INTEGER_2
	}

	void "sets year from and year to from WikitextToYearRangeProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookSeriesTemplateParameter.YEAR, value: YEARS)
		BookSeriesTemplate bookSeriesTemplate = new BookSeriesTemplate()
		YearRange yearRange = new YearRange(yearFrom: YEAR_FROM, yearTo: YEAR_TO)

		when:
		bookSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookSeriesTemplate))

		then:
		1 * wikitextToYearRangeProcessorMock.process(YEARS) >> yearRange
		0 * _
		bookSeriesTemplate.yearFrom == YEAR_FROM
		bookSeriesTemplate.yearTo == YEAR_TO
	}

	void "does not set year from and year to from WikitextToYearRangeProcessor, when value is already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookSeriesTemplateParameter.YEAR, value: YEARS)
		BookSeriesTemplate bookSeriesTemplate = new BookSeriesTemplate(yearFrom: YEAR_FROM)

		when:
		bookSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookSeriesTemplate))

		then:
		0 * _
		bookSeriesTemplate.yearFrom == YEAR_FROM
	}

	void "sets stardate from and stardate to from WikitextToStardateRangeProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookSeriesTemplateParameter.STARDATE, value: STARDATES)
		BookSeriesTemplate bookSeriesTemplate = new BookSeriesTemplate()
		StardateRange stardateRange = new StardateRange(stardateFrom: STARDATE_FROM, stardateTo: STARDATE_TO)

		when:
		bookSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookSeriesTemplate))

		then:
		1 * wikitextToStardateRangeProcessorMock.process(STARDATES) >> stardateRange
		0 * _
		bookSeriesTemplate.stardateFrom == STARDATE_FROM
		bookSeriesTemplate.stardateTo == STARDATE_TO
	}

	void "does not set set year from and year to from WikitextToYearRangeProcessor when YearRange is null"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicsTemplateParameter.YEAR, value: YEARS)
		BookSeriesTemplate bookSeriesTemplate = new BookSeriesTemplate()

		when:
		bookSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookSeriesTemplate))

		then:
		1 * wikitextToYearRangeProcessorMock.process(YEARS) >> null
		0 * _
		bookSeriesTemplate.yearFrom == null
		bookSeriesTemplate.yearTo == null
	}

	void "does not set year from and year to from WikitextToStardateRangeProcessor, when value is already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookSeriesTemplateParameter.STARDATE, value: STARDATES)
		BookSeriesTemplate bookSeriesTemplate = new BookSeriesTemplate(stardateFrom: STARDATE_FROM)

		when:
		bookSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookSeriesTemplate))

		then:
		0 * _
		bookSeriesTemplate.stardateFrom == STARDATE_FROM
	}

	void "sets miniseries flag from BookSeriesTemplateMiniseriesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookSeriesTemplateParameter.SERIES, value: SERIES)
		BookSeriesTemplate bookSeriesTemplate = new BookSeriesTemplate()

		when:
		bookSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookSeriesTemplate))

		then:
		1 * bookSeriesTemplateMiniseriesProcessorMock.process(SERIES) >> MINISERIES
		0 * _
		bookSeriesTemplate.miniseries == MINISERIES
	}

	void "does not set miniseries flag from BookSeriesTemplateMiniseriesProcessor, when value is already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: BookSeriesTemplateParameter.SERIES, value: SERIES)
		BookSeriesTemplate bookSeriesTemplate = new BookSeriesTemplate(miniseries: MINISERIES)

		when:
		bookSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), bookSeriesTemplate))

		then:
		0 * _
		bookSeriesTemplate.miniseries == MINISERIES
	}

}
