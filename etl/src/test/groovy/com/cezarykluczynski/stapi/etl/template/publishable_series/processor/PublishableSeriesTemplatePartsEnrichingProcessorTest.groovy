package com.cezarykluczynski.stapi.etl.template.publishable_series.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.WikitextToEntitiesProcessor
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplateParameter
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateRange
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToStardateRangeProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.WikitextToYearRangeProcessor
import com.cezarykluczynski.stapi.etl.template.publishable_series.dto.PublishableSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.publishable_series.dto.PublishableSeriesTemplateParameter
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import com.google.common.collect.Lists
import spock.lang.Specification

class PublishableSeriesTemplatePartsEnrichingProcessorTest extends Specification {

	private static final String PUBLISHER = 'PUBLISHER'
	private static final String PUBLISHED = 'PUBLISHED'
	private static final String YEARS = '1995-1997'
	private static final Integer YEAR = 1995
	private static final Integer YEAR_FROM = 1995
	private static final Integer YEAR_TO = 1997
	private static final String STARDATES = '1995-1997'
	private static final Float STARDATE_FROM = 123.4F
	private static final Float STARDATE_TO = 456.7F
	private static final String SERIES = 'SERIES'
	private static final Boolean MINISERIES = RandomUtil.nextBoolean()

	private WikitextToEntitiesProcessor wikitextToEntitiesProcessorMock

	private PublishableSeriesPublishedDatesEnrichingProcessor publishableSeriesPublishedDatesEnrichingProcessorMock

	private WikitextToYearRangeProcessor wikitextToYearRangeProcessorMock

	private WikitextToStardateRangeProcessor wikitextToStardateRangeProcessorMock

	private PublishableSeriesTemplateMiniseriesProcessor publishableSeriesTemplateMiniseriesProcessorMock

	private PublishableSeriesTemplatePartsEnrichingProcessor publishableSeriesTemplatePartsEnrichingProcessor

	void setup() {
		wikitextToEntitiesProcessorMock = Mock()
		publishableSeriesPublishedDatesEnrichingProcessorMock = Mock()
		wikitextToYearRangeProcessorMock = Mock()
		wikitextToStardateRangeProcessorMock = Mock()
		publishableSeriesTemplateMiniseriesProcessorMock = Mock()
		publishableSeriesTemplatePartsEnrichingProcessor = new PublishableSeriesTemplatePartsEnrichingProcessor(wikitextToEntitiesProcessorMock,
				publishableSeriesPublishedDatesEnrichingProcessorMock, wikitextToYearRangeProcessorMock, wikitextToStardateRangeProcessorMock,
				publishableSeriesTemplateMiniseriesProcessorMock)
	}

	void "sets publishers from PublishableSeriesTemplatePublishersProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: PublishableSeriesTemplateParameter.PUBLISHER, value: PUBLISHER)
		PublishableSeriesTemplate publishableSeriesTemplate = new PublishableSeriesTemplate()
		Company publisher1 = Mock()
		Company publisher2 = Mock()

		when:
		publishableSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), publishableSeriesTemplate))

		then:
		1 * wikitextToEntitiesProcessorMock.findCompanies(PUBLISHER) >> Lists.newArrayList(publisher1, publisher2)
		0 * _
		publishableSeriesTemplate.publishers.size() == 2
		publishableSeriesTemplate.publishers.contains publisher1
		publishableSeriesTemplate.publishers.contains publisher2
	}

	void "passes PublishableSeriesTemplate to PublishableSeriesPublishedDatesEnrichingProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: PublishableSeriesTemplateParameter.PUBLISHED, value: PUBLISHED)
		PublishableSeriesTemplate publishableSeriesTemplate = new PublishableSeriesTemplate()

		when:
		publishableSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), publishableSeriesTemplate))

		then:
		1 * publishableSeriesPublishedDatesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template.Part, PublishableSeriesTemplate> enrichablePair ->
				assert enrichablePair.input == templatePart
				assert enrichablePair.output != null
		}
		0 * _
	}

	void "does not pass PublishableSeriesTemplate to PublishableSeriesPublishedDatesEnrichingProcessor when is already have published years"() {
		given:
		Template.Part templatePart = new Template.Part(key: PublishableSeriesTemplateParameter.PUBLISHED, value: PUBLISHED)
		PublishableSeriesTemplate publishableSeriesTemplateWithPublishedYearFrom = new PublishableSeriesTemplate(publishedYearFrom: YEAR)
		PublishableSeriesTemplate publishableSeriesTemplateWithPublishedYearTo = new PublishableSeriesTemplate(publishedYearTo: YEAR)

		when:
		publishableSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart),
				publishableSeriesTemplateWithPublishedYearFrom))

		then:
		0 * _

		when:
		publishableSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart),
				publishableSeriesTemplateWithPublishedYearTo))

		then:
		0 * _
	}

	void "sets year from and year to from WikitextToYearRangeProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: PublishableSeriesTemplateParameter.YEAR, value: YEARS)
		PublishableSeriesTemplate publishableSeriesTemplate = new PublishableSeriesTemplate()
		YearRange yearRange = new YearRange(yearFrom: YEAR_FROM, yearTo: YEAR_TO)

		when:
		publishableSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), publishableSeriesTemplate))

		then:
		1 * wikitextToYearRangeProcessorMock.process(YEARS) >> yearRange
		0 * _
		publishableSeriesTemplate.yearFrom == YEAR_FROM
		publishableSeriesTemplate.yearTo == YEAR_TO
	}

	void "does not set year from and year to from WikitextToYearRangeProcessor, when value is already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: PublishableSeriesTemplateParameter.YEAR, value: YEARS)
		PublishableSeriesTemplate publishableSeriesTemplate = new PublishableSeriesTemplate(yearFrom: YEAR_FROM)

		when:
		publishableSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), publishableSeriesTemplate))

		then:
		0 * _
		publishableSeriesTemplate.yearFrom == YEAR_FROM
	}

	void "sets stardate from and stardate to from WikitextToStardateRangeProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: PublishableSeriesTemplateParameter.STARDATE, value: STARDATES)
		PublishableSeriesTemplate publishableSeriesTemplate = new PublishableSeriesTemplate()
		StardateRange stardateRange = StardateRange.of(STARDATE_FROM, STARDATE_TO)

		when:
		publishableSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), publishableSeriesTemplate))

		then:
		1 * wikitextToStardateRangeProcessorMock.process(STARDATES) >> stardateRange
		0 * _
		publishableSeriesTemplate.stardateFrom == STARDATE_FROM
		publishableSeriesTemplate.stardateTo == STARDATE_TO
	}

	void "does not set set year from and year to from WikitextToYearRangeProcessor when YearRange is null"() {
		given:
		Template.Part templatePart = new Template.Part(key: ComicsTemplateParameter.YEAR, value: YEARS)
		PublishableSeriesTemplate publishableSeriesTemplate = new PublishableSeriesTemplate()

		when:
		publishableSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), publishableSeriesTemplate))

		then:
		1 * wikitextToYearRangeProcessorMock.process(YEARS) >> null
		0 * _
		publishableSeriesTemplate.yearFrom == null
		publishableSeriesTemplate.yearTo == null
	}

	void "does not set year from and year to from WikitextToStardateRangeProcessor, when value is already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: PublishableSeriesTemplateParameter.STARDATE, value: STARDATES)
		PublishableSeriesTemplate publishableSeriesTemplate = new PublishableSeriesTemplate(stardateFrom: STARDATE_FROM)

		when:
		publishableSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), publishableSeriesTemplate))

		then:
		0 * _
		publishableSeriesTemplate.stardateFrom == STARDATE_FROM
	}

	void "sets miniseries flag from PublishableSeriesTemplateMiniseriesProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: PublishableSeriesTemplateParameter.SERIES, value: SERIES)
		PublishableSeriesTemplate publishableSeriesTemplate = new PublishableSeriesTemplate()

		when:
		publishableSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), publishableSeriesTemplate))

		then:
		1 * publishableSeriesTemplateMiniseriesProcessorMock.process(SERIES) >> MINISERIES
		0 * _
		publishableSeriesTemplate.miniseries == MINISERIES
	}

	void "does not set miniseries flag from PublishableSeriesTemplateMiniseriesProcessor, when value is already present"() {
		given:
		Template.Part templatePart = new Template.Part(key: PublishableSeriesTemplateParameter.SERIES, value: SERIES)
		PublishableSeriesTemplate publishableSeriesTemplate = new PublishableSeriesTemplate(miniseries: MINISERIES)

		when:
		publishableSeriesTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(templatePart), publishableSeriesTemplate))

		then:
		0 * _
		publishableSeriesTemplate.miniseries == MINISERIES
	}

}
