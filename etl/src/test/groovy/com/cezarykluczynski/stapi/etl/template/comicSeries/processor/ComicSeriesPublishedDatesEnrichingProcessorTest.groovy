package com.cezarykluczynski.stapi.etl.template.comicSeries.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.Range
import com.cezarykluczynski.stapi.etl.template.comicSeries.dto.ComicSeriesTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import com.cezarykluczynski.stapi.etl.template.common.service.TemplateToDayMonthYearParser
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

	private TemplateToDayMonthYearParser templateToDayMonthYearParserMock

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

	private final DayMonthYear yearlinkDayMonthYearFrom = DayMonthYear.of(null, null, YEAR_FROM)

	private final DayMonthYear yearlinkDayMonthYearTo = DayMonthYear.of(null, null, YEAR_TO)

	void setup() {
		templateFilterMock = Mock(TemplateFilter)
		templateToDayMonthYearParserMock = Mock(TemplateToDayMonthYearParser)
		comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock = Mock(ComicSeriesTemplateDayMonthYearRangeEnrichingProcessor)
		comicSeriesPublishedDatesEnrichingProcessor = new ComicSeriesPublishedDatesEnrichingProcessor(templateFilterMock,
				templateToDayMonthYearParserMock, comicSeriesTemplateDayMonthYearRangeEnrichingProcessorMock)
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
		1 * templateToDayMonthYearParserMock.parseDayMonthYearCandidate(datelinkTemplateFrom) >> datelinkDayMonthYearFrom
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
		1 * templateToDayMonthYearParserMock.parseDayMonthYearCandidate(datelinkTemplateFrom) >> datelinkDayMonthYearFrom
		1 * templateToDayMonthYearParserMock.parseDayMonthYearCandidate(datelinkTemplateTo) >> datelinkDayMonthYearTo
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
		1 * templateToDayMonthYearParserMock.parseMonthYearCandidate(monthlinkTemplateFrom) >> monthlinkDayMonthYearFrom
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
		1 * templateToDayMonthYearParserMock.parseMonthYearCandidate(monthlinkTemplateFrom) >> monthlinkDayMonthYearFrom
		1 * templateToDayMonthYearParserMock.parseMonthYearCandidate(monthlinkTemplateTo) >> monthlinkDayMonthYearTo
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
		1 * templateToDayMonthYearParserMock.parseYearCandidate(yearlinkTemplateFrom) >> yearlinkDayMonthYearFrom
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
		1 * templateToDayMonthYearParserMock.parseYearCandidate(yearlinkTemplateFrom) >> yearlinkDayMonthYearFrom
		1 * templateToDayMonthYearParserMock.parseYearCandidate(yearlinkTemplateTo) >> yearlinkDayMonthYearTo
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
		1 * templateToDayMonthYearParserMock.parseYearCandidate(yearlinkTemplateFrom) >> null
		1 * templateToDayMonthYearParserMock.parseYearCandidate(yearlinkTemplateTo) >> null
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
