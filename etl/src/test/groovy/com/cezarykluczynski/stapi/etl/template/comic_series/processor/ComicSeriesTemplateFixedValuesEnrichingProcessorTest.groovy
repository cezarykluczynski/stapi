package com.cezarykluczynski.stapi.etl.template.comic_series.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.common.dto.Range
import com.cezarykluczynski.stapi.etl.template.book_series.dto.BookSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.comic_series.dto.ComicSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateYearDTO
import com.cezarykluczynski.stapi.etl.template.publishable_series.processor.PublishableSeriesTemplateDayMonthYearRangeEnrichingProcessor
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class ComicSeriesTemplateFixedValuesEnrichingProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final Integer NUMBER_OF_ISSUES = 14
	private static final Float STARDATE_FROM = 1514.2F
	private static final Float STARDATE_TO = 1517.5F
	private static final Integer YEAR_FROM = 2350
	private static final Integer YEAR_TO = 2390

	private ComicSeriesPublishedDateFixedValueProvider comicSeriesPublishedDateFixedValueProviderMock

	private PublishableSeriesTemplateDayMonthYearRangeEnrichingProcessor publishableSeriesTemplateDayMonthYearRangeEnrichingProcessorMock

	private ComicSeriesTemplateNumberOfIssuesFixedValueProvider comicSeriesTemplateNumberOfIssuesFixedValueProviderMock

	private ComicSeriesStardateYearFixedValueProvider comicSeriesStardateYearFixedValueProviderMock

	private ComicSeriesTemplateFixedValuesEnrichingProcessor comicSeriesTemplateFixedValuesEnrichingProcessor

	void setup() {
		comicSeriesPublishedDateFixedValueProviderMock = Mock()
		publishableSeriesTemplateDayMonthYearRangeEnrichingProcessorMock = Mock()
		comicSeriesTemplateNumberOfIssuesFixedValueProviderMock = Mock()
		comicSeriesStardateYearFixedValueProviderMock = Mock()
		comicSeriesTemplateFixedValuesEnrichingProcessor = new ComicSeriesTemplateFixedValuesEnrichingProcessor(
				comicSeriesPublishedDateFixedValueProviderMock, publishableSeriesTemplateDayMonthYearRangeEnrichingProcessorMock,
				comicSeriesTemplateNumberOfIssuesFixedValueProviderMock, comicSeriesStardateYearFixedValueProviderMock)
	}

	void "when fixed value for published dates is found, it is passed to enriching processor"() {
		given:
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate(title: TITLE)
		Range<DayMonthYear> dayMonthYearRange = Mock()

		when:
		comicSeriesTemplateFixedValuesEnrichingProcessor.enrich(EnrichablePair.of(comicSeriesTemplate, comicSeriesTemplate))

		then:
		1 * comicSeriesPublishedDateFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.found(dayMonthYearRange)
		1 * publishableSeriesTemplateDayMonthYearRangeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Pair<Template.Part, Range<DayMonthYear>>, BookSeriesTemplate> enrichablePair ->
				assert enrichablePair.input.key == null
				assert enrichablePair.input.value == dayMonthYearRange
				assert enrichablePair.output != null
		}
		1 * comicSeriesTemplateNumberOfIssuesFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.empty()
		1 * comicSeriesStardateYearFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.empty()
		0 * _
		comicSeriesTemplate.title == TITLE
	}

	void "when fixed value for published dates is found, it is set to entity"() {
		given:
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate(title: TITLE)

		when:
		comicSeriesTemplateFixedValuesEnrichingProcessor.enrich(EnrichablePair.of(comicSeriesTemplate, comicSeriesTemplate))

		then:
		1 * comicSeriesPublishedDateFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.empty()
		1 * comicSeriesTemplateNumberOfIssuesFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.found(NUMBER_OF_ISSUES)
		1 * comicSeriesStardateYearFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.empty()
		0 * _
		comicSeriesTemplate.numberOfIssues == NUMBER_OF_ISSUES
	}

	void "when fixed value for stardate and year is found, it is set to entity"() {
		given:
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate(title: TITLE)
		StardateYearDTO stardateYearDTO = StardateYearDTO.of(STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO)

		when:
		comicSeriesTemplateFixedValuesEnrichingProcessor.enrich(EnrichablePair.of(comicSeriesTemplate, comicSeriesTemplate))

		then:
		1 * comicSeriesPublishedDateFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.empty()
		1 * comicSeriesTemplateNumberOfIssuesFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.empty()
		1 * comicSeriesStardateYearFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.found(stardateYearDTO)
		0 * _
		comicSeriesTemplate.stardateFrom == STARDATE_FROM
		comicSeriesTemplate.stardateTo == STARDATE_TO
		comicSeriesTemplate.yearFrom == YEAR_FROM
		comicSeriesTemplate.yearTo == YEAR_TO
	}

}
