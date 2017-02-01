package com.cezarykluczynski.stapi.etl.template.comicSeries.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.Range
import com.cezarykluczynski.stapi.etl.template.comicSeries.dto.ComicSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatelinkTemplateToDayMonthYearProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.MonthlinkTemplateToMonthYearProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.YearlinkToYearProcessor
import com.cezarykluczynski.stapi.etl.template.service.TemplateFilter
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateName
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicSeriesPublishedDatesEnrichingProcessorTest extends Specification {

	private static final Integer DAY_FROM = 4
	private static final Integer MONTH_FROM = 6
	private static final Integer YEAR_FROM = 1979
	private static final Integer DAY_TO = 8
	private static final Integer MONTH_TO = 17
	private static final Integer YEAR_TO = 1981

	private TemplateFilter templateFilterMock

	private DatelinkTemplateToDayMonthYearProcessor datelinkTemplateToDayMonthYearProcessorMock

	private MonthlinkTemplateToMonthYearProcessor monthlinkTemplateToMonthYearProcessorMock

	private YearlinkToYearProcessor yearlinkToYearProcessorMock

	private ComicSeriesTemplateDayMonthYearRangeEnrichingProcessor comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock

	private ComicSeriesPublishedDatesEnrichingProcessor comicSeriesPublishedDatesEnrichingProcessor

	private final Template datelinkTemplateFrom = Mock(Template)

	private final Template datelinkTemplateTo = Mock(Template)

	private final Template monthlinkTemplateFrom = Mock(Template)

	private final Template monthlinkTemplateTo = Mock(Template)

	private final Template yearlinkTemplateFrom = Mock(Template)

	private final Template yearlinkTemplateTo = Mock(Template)

	private final DayMonthYear datelinkDayMonthYearFrom = DayMonthYear.of(DAY_FROM, MONTH_FROM, YEAR_FROM)

	private final DayMonthYear datelinkDayMonthYearTo = DayMonthYear.of(DAY_TO, MONTH_TO, YEAR_TO)

	private final DayMonthYear monthlinkDayMonthYearFrom = DayMonthYear.of(null, MONTH_FROM, YEAR_FROM)

	private final DayMonthYear monthlinkDayMonthYearTo = DayMonthYear.of(null, MONTH_TO, YEAR_TO)

	void setup() {
		templateFilterMock = Mock(TemplateFilter)
		datelinkTemplateToDayMonthYearProcessorMock = Mock(DatelinkTemplateToDayMonthYearProcessor)
		monthlinkTemplateToMonthYearProcessorMock = Mock(MonthlinkTemplateToMonthYearProcessor)
		yearlinkToYearProcessorMock = Mock(YearlinkToYearProcessor)
		comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock = Mock(ComicSeriesTemplateDayMonthYearRangeEnrichingProcessor)
		comicSeriesPublishedDatesEnrichingProcessor = new ComicSeriesPublishedDatesEnrichingProcessor(templateFilterMock,
				datelinkTemplateToDayMonthYearProcessorMock, monthlinkTemplateToMonthYearProcessorMock, yearlinkToYearProcessorMock,
				comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock)
	}

	void "when single datelink template is found, it is used to populate star and end publication dates"() {
		given:
		Template.Part templatePart = new Template.Part()
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()

		when:
		comicSeriesPublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicSeriesTemplate))

		then:
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.D, TemplateName.DATELINK) >> Lists.newArrayList(datelinkTemplateFrom)
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.M, TemplateName.MONTHLINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.Y, TemplateName.YEARLINK) >> Lists.newArrayList()
		1 * datelinkTemplateToDayMonthYearProcessorMock.process(datelinkTemplateFrom) >> datelinkDayMonthYearFrom
		1 * comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Range<DayMonthYear>, ComicSeriesTemplate> enrichablePair ->
			assert enrichablePair.input.from.day == DAY_FROM
			assert enrichablePair.input.from.month == MONTH_FROM
			assert enrichablePair.input.from.year == YEAR_FROM
			assert enrichablePair.input.to == null
			assert enrichablePair.output != null
		}
		0 * _
	}

	void "when both datelink templates are found, they are used to populate star and end publication dates"() {
		given:
		Template.Part templatePart = new Template.Part()
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()
		when:
		comicSeriesPublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicSeriesTemplate))

		then:
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.D, TemplateName.DATELINK) >> Lists.newArrayList(
				datelinkTemplateFrom, datelinkTemplateTo)
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.M, TemplateName.MONTHLINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.Y, TemplateName.YEARLINK) >> Lists.newArrayList()
		1 * datelinkTemplateToDayMonthYearProcessorMock.process(datelinkTemplateFrom) >> datelinkDayMonthYearFrom
		1 * datelinkTemplateToDayMonthYearProcessorMock.process(datelinkTemplateTo) >> datelinkDayMonthYearTo
		1 * comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Range<DayMonthYear>, ComicSeriesTemplate> enrichablePair ->
			assert enrichablePair.input.from.day == DAY_FROM
			assert enrichablePair.input.from.month == MONTH_FROM
			assert enrichablePair.input.from.year == YEAR_FROM
			assert enrichablePair.input.to.day == DAY_TO
			assert enrichablePair.input.to.month == MONTH_TO
			assert enrichablePair.input.to.year == YEAR_TO
			assert enrichablePair.output != null
		}
		0 * _
	}

	void "when single monthlink template is found, it is used to populate star and end publication dates"() {
		given:
		Template.Part templatePart = new Template.Part()
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()

		when:
		comicSeriesPublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicSeriesTemplate))

		then:
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.D, TemplateName.DATELINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.M, TemplateName.MONTHLINK) >> Lists
				.newArrayList(monthlinkTemplateFrom)
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.Y, TemplateName.YEARLINK) >> Lists.newArrayList()
		1 * monthlinkTemplateToMonthYearProcessorMock.process(monthlinkTemplateFrom) >> monthlinkDayMonthYearFrom
		1 * comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Range<DayMonthYear>, ComicSeriesTemplate> enrichablePair ->
			assert enrichablePair.input.from.day == null
			assert enrichablePair.input.from.month == MONTH_FROM
			assert enrichablePair.input.from.year == YEAR_FROM
			assert enrichablePair.input.to == null
			assert enrichablePair.output != null
		}
		0 * _
	}

	void "when both monthlink templates are found, they are used to populate star and end publication dates"() {
		given:
		Template.Part templatePart = new Template.Part()
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()

		when:
		comicSeriesPublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicSeriesTemplate))

		then:
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.D, TemplateName.DATELINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.M, TemplateName.MONTHLINK) >> Lists
				.newArrayList(monthlinkTemplateFrom, monthlinkTemplateTo)
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.Y, TemplateName.YEARLINK) >> Lists.newArrayList()
		1 * monthlinkTemplateToMonthYearProcessorMock.process(monthlinkTemplateFrom) >> monthlinkDayMonthYearFrom
		1 * monthlinkTemplateToMonthYearProcessorMock.process(monthlinkTemplateTo) >> monthlinkDayMonthYearTo
		1 * comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Range<DayMonthYear>, ComicSeriesTemplate> enrichablePair ->
			assert enrichablePair.input.from.day == null
			assert enrichablePair.input.from.month == MONTH_FROM
			assert enrichablePair.input.from.year == YEAR_FROM
			assert enrichablePair.input.to.day == null
			assert enrichablePair.input.to.month == MONTH_TO
			assert enrichablePair.input.to.year == YEAR_TO
			assert enrichablePair.output != null
		}
		0 * _
	}

	void "when single yearlink template is found, it is used to populate star and end publication dates"() {
		given:
		Template.Part templatePart = new Template.Part()
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()

		when:
		comicSeriesPublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicSeriesTemplate))

		then:
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.D, TemplateName.DATELINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.M, TemplateName.MONTHLINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.Y, TemplateName.YEARLINK) >> Lists.newArrayList(yearlinkTemplateFrom)
		1 * yearlinkToYearProcessorMock.process(yearlinkTemplateFrom) >> YEAR_FROM
		1 * comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Range<DayMonthYear>, ComicSeriesTemplate> enrichablePair ->
				assert enrichablePair.input.from.day == null
				assert enrichablePair.input.from.month == null
				assert enrichablePair.input.from.year == YEAR_FROM
				assert enrichablePair.input.to == null
				assert enrichablePair.output != null
		}
		0 * _
	}

	void "when both yearlink templates are found, they are used to populate star and end publication dates"() {
		given:
		Template.Part templatePart = new Template.Part()
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()

		when:
		comicSeriesPublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicSeriesTemplate))

		then:
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.D, TemplateName.DATELINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.M, TemplateName.MONTHLINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.Y, TemplateName.YEARLINK) >> Lists.newArrayList(yearlinkTemplateFrom,
				yearlinkTemplateTo)
		1 * yearlinkToYearProcessorMock.process(yearlinkTemplateFrom) >> YEAR_FROM
		1 * yearlinkToYearProcessorMock.process(yearlinkTemplateTo) >> YEAR_TO
		1 * comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Range<DayMonthYear>, ComicSeriesTemplate> enrichablePair ->
				assert enrichablePair.input.from.day == null
				assert enrichablePair.input.from.month == null
				assert enrichablePair.input.from.year == YEAR_FROM
				assert enrichablePair.input.to.day == null
				assert enrichablePair.input.to.month == null
				assert enrichablePair.input.to.year == YEAR_TO
				assert enrichablePair.output != null
		}
		0 * _
	}

	void "when both yearlink templates are found, but could not be parsed, null values are passed"() {
		given:
		Template.Part templatePart = new Template.Part()
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()

		when:
		comicSeriesPublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicSeriesTemplate))

		then:
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.D, TemplateName.DATELINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.M, TemplateName.MONTHLINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.Y, TemplateName.YEARLINK) >> Lists.newArrayList(yearlinkTemplateFrom,
				yearlinkTemplateTo)
		1 * yearlinkToYearProcessorMock.process(yearlinkTemplateFrom) >> null
		1 * yearlinkToYearProcessorMock.process(yearlinkTemplateTo) >> null
		1 * comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Range<DayMonthYear>, ComicSeriesTemplate> enrichablePair ->
				assert enrichablePair.input.from == null
				assert enrichablePair.input.to == null
				assert enrichablePair.output != null
		}
		0 * _
	}

	void "does not pass anything to enricher is nothing was found"() {
		given:
		Template.Part templatePart = new Template.Part()
		ComicSeriesTemplate comicSeriesTemplate = new ComicSeriesTemplate()

		when:
		comicSeriesPublishedDatesEnrichingProcessor.enrich(EnrichablePair.of(templatePart, comicSeriesTemplate))

		then:
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.D, TemplateName.DATELINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.M, TemplateName.MONTHLINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateName.Y, TemplateName.YEARLINK) >> Lists.newArrayList()
		0 * _
	}

}
