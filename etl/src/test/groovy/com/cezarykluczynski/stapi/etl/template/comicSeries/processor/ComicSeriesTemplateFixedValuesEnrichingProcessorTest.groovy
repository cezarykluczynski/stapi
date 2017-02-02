package com.cezarykluczynski.stapi.etl.template.comicSeries.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.common.dto.Range
import com.cezarykluczynski.stapi.etl.template.comicSeries.dto.ComicSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateYearDTO
import spock.lang.Specification

class ComicSeriesTemplateFixedValuesEnrichingProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final Integer NUMBER_OF_ISSUES = 14
	private static final Float STARDATE_FROM = 1514.2F
	private static final Float STARDATE_TO = 1517.5F
	private static final Integer YEAR_FROM = 2350
	private static final Integer YEAR_TO = 2390

	private ComicSeriesPublishedDateFixedValueProvider comicSeriesPublishedDateFixedValueProviderMock

	private ComicSeriesTemplateDayMonthYearRangeEnrichingProcessor comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock

	private ComicSeriesTemplateNumberOfIssuesFixedValueProvider comicSeriesTemplateNumberOfIssuesFixedValueProviderMock

	private ComicSeriesStardateYearFixedValueProvider comicSeriesStardateYearFixedValueProviderMock

	private ComicSeriesTemplateFixedValuesEnrichingProcessor comicSeriesTemplateFixedValuesEnrichingProcessor

	void setup() {
		comicSeriesPublishedDateFixedValueProviderMock = Mock(ComicSeriesPublishedDateFixedValueProvider)
		comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock = Mock(ComicSeriesTemplateDayMonthYearRangeEnrichingProcessor)
		comicSeriesTemplateNumberOfIssuesFixedValueProviderMock = Mock(ComicSeriesTemplateNumberOfIssuesFixedValueProvider)
		comicSeriesStardateYearFixedValueProviderMock = Mock(ComicSeriesStardateYearFixedValueProvider)
		comicSeriesTemplateFixedValuesEnrichingProcessor = new ComicSeriesTemplateFixedValuesEnrichingProcessor(
				comicSeriesPublishedDateFixedValueProviderMock, comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock,
				comicSeriesTemplateNumberOfIssuesFixedValueProviderMock, comicSeriesStardateYearFixedValueProviderMock)
	}

	void "when fixed value for published dates is found, it is passed to enriching processor"() {
		given:
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate(title: TITLE)
		Range<DayMonthYear> dayMonthYearRange = Mock(Range)

		when:
		comicSeriesTemplateFixedValuesEnrichingProcessor.enrich(EnrichablePair.of(comicSeriesTemplate, comicSeriesTemplate))

		then:
		1 * comicSeriesPublishedDateFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.found(dayMonthYearRange)
		1 * comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Range<DayMonthYear>, ComicSeriesTemplate> enrichablePair ->
				assert enrichablePair.input == dayMonthYearRange
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
