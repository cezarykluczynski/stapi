package com.cezarykluczynski.stapi.etl.template.comic_strip.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.Range
import com.cezarykluczynski.stapi.etl.template.comic_strip.dto.ComicStripTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import spock.lang.Specification

class ComicStripTemplateDayMonthYearRangeEnrichingProcessorTest extends Specification {

	private static final Integer DAY_FROM = 4
	private static final Integer MONTH_FROM = 6
	private static final Integer YEAR_FROM = 1979
	private static final Integer DAY_TO = 8
	private static final Integer MONTH_TO = 11
	private static final Integer YEAR_TO = 1981
	private static final DayMonthYear DAY_MONTH_YEAR_FROM = DayMonthYear.of(DAY_FROM, MONTH_FROM, YEAR_FROM)
	private static final DayMonthYear DAY_MONTH_YEAR_TO = DayMonthYear.of(DAY_TO, MONTH_TO, YEAR_TO)

	private ComicStripTemplateDayMonthYearRangeEnrichingProcessor comicStripTemplateDayMonthYearRangeEnrichingProcessor

	void setup() {
		comicStripTemplateDayMonthYearRangeEnrichingProcessor = new ComicStripTemplateDayMonthYearRangeEnrichingProcessor()
	}

	void "when either value is null, no exception is thrown"() {
		when:
		ComicStripTemplate comicStripTemplate = Mock()
		comicStripTemplateDayMonthYearRangeEnrichingProcessor.enrich(EnrichablePair.of(null, comicStripTemplate))

		then:
		0 * _
		notThrown(Exception)

		when:
		Range range = Mock()
		comicStripTemplateDayMonthYearRangeEnrichingProcessor.enrich(EnrichablePair.of(range, null))

		then:
		0 * _
		notThrown(Exception)
	}

	void "when both range values are null, nothing is set to template"() {
		given:
		ComicStripTemplate comicStripTemplate = Mock()

		when:
		comicStripTemplateDayMonthYearRangeEnrichingProcessor.enrich(EnrichablePair.of(Range.of(null, null), comicStripTemplate))

		then:
		0 * _
	}

	void "when only from date is present, it is used to populate entire template"() {
		given:
		ComicStripTemplate comicStripTemplate = new ComicStripTemplate()

		when:
		comicStripTemplateDayMonthYearRangeEnrichingProcessor.enrich(EnrichablePair.of(Range.of(DAY_MONTH_YEAR_FROM, null), comicStripTemplate))

		then:
		comicStripTemplate.publishedYearFrom == YEAR_FROM
		comicStripTemplate.publishedMonthFrom == MONTH_FROM
		comicStripTemplate.publishedDayFrom == DAY_FROM
		comicStripTemplate.publishedYearTo == YEAR_FROM
		comicStripTemplate.publishedMonthTo == MONTH_FROM
		comicStripTemplate.publishedDayTo == DAY_FROM
	}

	void "when from and to dates are present, they are used to populate template accordingly"() {
		given:
		ComicStripTemplate comicStripTemplate = new ComicStripTemplate()

		when:
		comicStripTemplateDayMonthYearRangeEnrichingProcessor.enrich(EnrichablePair.of(Range.of(DAY_MONTH_YEAR_FROM, DAY_MONTH_YEAR_TO),
				comicStripTemplate))

		then:
		comicStripTemplate.publishedYearFrom == YEAR_FROM
		comicStripTemplate.publishedMonthFrom == MONTH_FROM
		comicStripTemplate.publishedDayFrom == DAY_FROM
		comicStripTemplate.publishedYearTo == YEAR_TO
		comicStripTemplate.publishedMonthTo == MONTH_TO
		comicStripTemplate.publishedDayTo == DAY_TO
	}

}
