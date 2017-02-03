package com.cezarykluczynski.stapi.etl.template.comicSeries.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.comicSeries.dto.ComicSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateRange
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToStardateRangeProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToYearRangeProcessor
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.tool.LogicUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicSeriesTemplatePartsEnrichingProcessorTest extends Specification {

	private static final String PUBLISHER = 'PUBLISHER'
	private static final String PUBLISHED = 'PUBLISHED'
	private static final String YEARS = '1995-1997'
	private static final Integer YEAR = 1995
	private static final Integer YEAR_FROM = 1995
	private static final Integer YEAR_TO = 1997
	private static final String STARDATES = '1995-1997'
	private static final Float STARDATE_FROM = 123.4F
	private static final Float STARDATE_TO = 456.7F
	private static final String ISSUES_STRING = '12'
	private static final Integer ISSUES_INTEGER = 12
	private static final String SERIES = 'SERIES'
	private static final Boolean MINISERIES = LogicUtil.nextBoolean()

	private ComicSeriesTemplatePublishersProcessor comicSeriesTemplatePublishersProcessorMock

	private ComicSeriesPublishedDatesEnrichingProcessor comicSeriesPublishedDatesEnrichingProcessorMock

	private ComicSeriesTemplateNumberOfIssuesProcessor comicSeriesTemplateNumberOfIssuesProcessorMock

	private WikitextToYearRangeProcessor wikitextToYearRangeProcessorMock

	private WikitextToStardateRangeProcessor wikitextToStardateRangeProcessorMock

	private ComicSeriesTemplateMiniseriesProcessor comicSeriesTemplateMiniseriesProcessorMock

	private ComicSeriesTemplatePartsEnrichingProcessor comicSeriesTemplatePartsEnrichingProcessor

	void setup() {
		comicSeriesTemplatePublishersProcessorMock = Mock(ComicSeriesTemplatePublishersProcessor)
		comicSeriesPublishedDatesEnrichingProcessorMock = Mock(ComicSeriesPublishedDatesEnrichingProcessor)
		comicSeriesTemplateNumberOfIssuesProcessorMock = Mock(ComicSeriesTemplateNumberOfIssuesProcessor)
		wikitextToYearRangeProcessorMock = Mock(WikitextToYearRangeProcessor)
		wikitextToStardateRangeProcessorMock = Mock(WikitextToStardateRangeProcessor)
		comicSeriesTemplateMiniseriesProcessorMock = Mock(ComicSeriesTemplateMiniseriesProcessor)
		comicSeriesTemplatePartsEnrichingProcessor = new ComicSeriesTemplatePartsEnrichingProcessor(comicSeriesTemplatePublishersProcessorMock,
				comicSeriesPublishedDatesEnrichingProcessorMock, comicSeriesTemplateNumberOfIssuesProcessorMock, wikitextToYearRangeProcessorMock,
				wikitextToStardateRangeProcessorMock, comicSeriesTemplateMiniseriesProcessorMock)
	}

	void "sets publishers from ComicSeriesTemplatePublishersProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicSeriesTemplatePartsEnrichingProcessor.PUBLISHER, value: PUBLISHER)
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()
		Set publisherSet = Mock(Set)

		when:
		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicSeriesTemplate))

		then:
		1 * comicSeriesTemplatePublishersProcessorMock.process(PUBLISHER) >> publisherSet
		0 * _
		comicSeriesTemplate.publishers == publisherSet
	}

	void "passes ComicSeriesTemplate to ComicSeriesPublishedDatesEnrichingProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicSeriesTemplatePartsEnrichingProcessor.PUBLISHED, value: PUBLISHED)
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()

		when:
		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicSeriesTemplate))

		then:
		1 * comicSeriesPublishedDatesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Template.Part, ComicSeriesTemplate> enrichablePair ->
			assert enrichablePair.input == templatePart
			assert enrichablePair.output != null
		}
		0 * _
	}

	void "does not pass ComicSeriesTemplate to ComicSeriesPublishedDatesEnrichingProcessor when is already have published years"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicSeriesTemplatePartsEnrichingProcessor.PUBLISHED, value: PUBLISHED)
		ComicSeriesTemplate comicSeriesTemplateWithPublishedYearFrom = new ComicSeriesTemplate(publishedYearFrom: YEAR)
		ComicSeriesTemplate comicSeriesTemplateWithPublishedYearTo = new ComicSeriesTemplate(publishedYearTo: YEAR)

		when:
		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart),
				comicSeriesTemplateWithPublishedYearFrom))

		then:
		0 * _

		when:
		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart),
				comicSeriesTemplateWithPublishedYearTo))

		then:
		0 * _
	}

	void "sets number of issues from ComicSeriesTemplateNumberOfIssuesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicSeriesTemplatePartsEnrichingProcessor.ISSUES, value: ISSUES_STRING)
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()

		when:
		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicSeriesTemplate))

		then:
		1 * comicSeriesTemplateNumberOfIssuesProcessorMock.process(ISSUES_STRING) >> ISSUES_INTEGER
		0 * _
		comicSeriesTemplate.numberOfIssues == ISSUES_INTEGER
	}

	void "does not set number of issues from ComicSeriesTemplateNumberOfIssuesProcessor, when value is already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicSeriesTemplatePartsEnrichingProcessor.ISSUES, value: ISSUES_STRING)
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate(numberOfIssues: ISSUES_INTEGER)

		when:
		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicSeriesTemplate))

		then:
		0 * _
		comicSeriesTemplate.numberOfIssues == ISSUES_INTEGER
	}

	void "sets year from and year to from WikitextToYearRangeProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicSeriesTemplatePartsEnrichingProcessor.YEAR, value: YEARS)
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()
		YearRange yearRange = new YearRange(startYear: YEAR_FROM, endYear: YEAR_TO)

		when:
		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicSeriesTemplate))

		then:
		1 * wikitextToYearRangeProcessorMock.process(YEARS) >> yearRange
		0 * _
		comicSeriesTemplate.yearFrom == YEAR_FROM
		comicSeriesTemplate.yearTo == YEAR_TO
	}

	void "does not set year from and year to from WikitextToYearRangeProcessor, when value is already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicSeriesTemplatePartsEnrichingProcessor.YEAR, value: ISSUES_STRING)
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate(yearFrom: YEAR_FROM)

		when:
		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicSeriesTemplate))

		then:
		0 * _
		comicSeriesTemplate.yearFrom == YEAR_FROM
	}

	void "sets stardate from and stardate to from WikitextToStardateRangeProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicSeriesTemplatePartsEnrichingProcessor.STARDATE, value: STARDATES)
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()
		StardateRange stardateRange = new StardateRange(stardateFrom: STARDATE_FROM, stardateTo: STARDATE_TO)

		when:
		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicSeriesTemplate))

		then:
		1 * wikitextToStardateRangeProcessorMock.process(STARDATES) >> stardateRange
		0 * _
		comicSeriesTemplate.stardateFrom == STARDATE_FROM
		comicSeriesTemplate.stardateTo == STARDATE_TO
	}

	void "does not set year from and year to from WikitextToStardateRangeProcessor, when value is already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicSeriesTemplatePartsEnrichingProcessor.STARDATE, value: STARDATES)
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate(stardateFrom: STARDATE_FROM)

		when:
		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicSeriesTemplate))

		then:
		0 * _
		comicSeriesTemplate.stardateFrom == STARDATE_FROM
	}

	void "sets miniseries flag from ComicSeriesTemplateMiniseriesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicSeriesTemplatePartsEnrichingProcessor.SERIES, value: SERIES)
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()

		when:
		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicSeriesTemplate))

		then:
		1 * comicSeriesTemplateMiniseriesProcessorMock.process(SERIES) >> MINISERIES
		0 * _
		comicSeriesTemplate.miniseries == MINISERIES
	}

	void "does not set miniseries flag from ComicSeriesTemplateMiniseriesProcessor, when value is already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicSeriesTemplatePartsEnrichingProcessor.SERIES, value: SERIES)
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate(miniseries: MINISERIES)

		when:
		comicSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), comicSeriesTemplate))

		then:
		0 * _
		comicSeriesTemplate.miniseries == MINISERIES
	}

}
