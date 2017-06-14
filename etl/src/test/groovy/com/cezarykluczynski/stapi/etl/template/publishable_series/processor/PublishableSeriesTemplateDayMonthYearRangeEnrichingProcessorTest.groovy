package com.cezarykluczynski.stapi.etl.template.publishable_series.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.Range
import com.cezarykluczynski.stapi.etl.template.comic_series.dto.ComicSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import com.cezarykluczynski.stapi.etl.template.publishable_series.dto.PublishableSeriesTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import org.apache.commons.lang3.tuple.Pair
import org.assertj.core.util.Lists
import spock.lang.Specification

class PublishableSeriesTemplateDayMonthYearRangeEnrichingProcessorTest extends Specification {

	private static final Integer DAY_FROM = 4
	private static final Integer MONTH_FROM = 6
	private static final Integer YEAR_FROM = 1979
	private static final Integer DAY_TO = 8
	private static final Integer MONTH_TO = 11
	private static final Integer YEAR_TO = 1981
	private static final DayMonthYear DAY_MONTH_YEAR_FROM = DayMonthYear.of(DAY_FROM, MONTH_FROM, YEAR_FROM)
	private static final DayMonthYear DAY_MONTH_YEAR_TO = DayMonthYear.of(DAY_TO, MONTH_TO, YEAR_TO)

	private PublishableSeriesTemplateDayMonthYearRangeEnrichingProcessor publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor

	void setup() {
		publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor = new PublishableSeriesTemplateDayMonthYearRangeEnrichingProcessor()
	}

	void "when either value is null, no exception is thrown"() {
		given:
		PublishableSeriesTemplate publishableSeriesTemplate = Mock()
		Range range = Mock()

		when:
		publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor.enrich(EnrichablePair.of(Pair.of(null, null), publishableSeriesTemplate))

		then:
		0 * _
		notThrown(Exception)

		when:
		publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor.enrich(EnrichablePair.of(Pair.of(null, range), null))

		then:
		0 * _
		notThrown(Exception)
	}

	void "when both range values are null, nothing is set to template"() {
		given:
		Template.Part templatePart = new Template.Part()
		Range range = Range.of(null, null)
		ComicSeriesTemplate comicSeriesTemplate = Mock()

		when:
		publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor.enrich(EnrichablePair.of(Pair.of(templatePart, range), comicSeriesTemplate))

		then:
		0 * _
	}

	void "when only from date is present, it is used to populate entire template"() {
		given:
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()

		when:
		publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor
				.enrich(EnrichablePair.of(Pair.of(null, Range.of(DAY_MONTH_YEAR_FROM, null)), comicSeriesTemplate))

		then:
		comicSeriesTemplate.publishedYearFrom == YEAR_FROM
		comicSeriesTemplate.publishedMonthFrom == MONTH_FROM
		comicSeriesTemplate.publishedDayFrom == DAY_FROM
		comicSeriesTemplate.publishedYearTo == YEAR_FROM
		comicSeriesTemplate.publishedMonthTo == MONTH_FROM
		comicSeriesTemplate.publishedDayTo == DAY_FROM
	}

	void "when from and to dates are present, they are used to populate template accordingly"() {
		given:
		Template.Part templatePart = new Template.Part()
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()

		when:
		publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor
				.enrich(EnrichablePair.of(Pair.of(templatePart, Range.of(DAY_MONTH_YEAR_FROM, DAY_MONTH_YEAR_TO)), comicSeriesTemplate))

		then:
		comicSeriesTemplate.publishedYearFrom == YEAR_FROM
		comicSeriesTemplate.publishedMonthFrom == MONTH_FROM
		comicSeriesTemplate.publishedDayFrom == DAY_FROM
		comicSeriesTemplate.publishedYearTo == YEAR_TO
		comicSeriesTemplate.publishedMonthTo == MONTH_TO
		comicSeriesTemplate.publishedDayTo == DAY_TO
	}

	void "when any dash is in template part content, and there is one template in part, published date to is not set"() {
		given:
		Template template = new Template()
		Template.Part templatePart = new Template.Part(
				content: PublishableSeriesTemplateDayMonthYearRangeEnrichingProcessor.DASHES[0],
				templates: Lists.newArrayList(template))
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()

		when:
		publishableSeriesTemplateDayMonthYearRangeEnrichingProcessor
				.enrich(EnrichablePair.of(Pair.of(templatePart, Range.of(DAY_MONTH_YEAR_FROM, null)), comicSeriesTemplate))

		then:
		comicSeriesTemplate.publishedYearFrom == YEAR_FROM
		comicSeriesTemplate.publishedMonthFrom == MONTH_FROM
		comicSeriesTemplate.publishedDayFrom == DAY_FROM
		comicSeriesTemplate.publishedYearTo == null
		comicSeriesTemplate.publishedMonthTo == null
		comicSeriesTemplate.publishedDayTo == null
	}

}
