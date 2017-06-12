package com.cezarykluczynski.stapi.etl.template.common.processor

import com.cezarykluczynski.stapi.etl.common.dto.Range
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import com.cezarykluczynski.stapi.etl.template.common.service.TemplateToDayMonthYearParser
import com.cezarykluczynski.stapi.etl.template.service.TemplateFilter
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class DayMonthYearRangeProcessorTest extends Specification {

	private static final Integer DAY_FROM = 4
	private static final Integer MONTH_FROM = 6
	private static final Integer YEAR_FROM = 1979
	private static final Integer DAY_TO = 8
	private static final Integer MONTH_TO = 11
	private static final Integer YEAR_TO = 1981

	private TemplateFilter templateFilterMock

	private TemplateToDayMonthYearParser templateToDayMonthYearParserMock

	private DayMonthYearRangeProcessor dayMonthYearRangeProcessor

	private final Template datelinkTemplateFrom = Mock()

	private final Template datelinkTemplateTo = Mock()

	private final Template monthlinkTemplateFrom = Mock()

	private final Template monthlinkTemplateTo = Mock()

	private final Template yearlinkTemplateFrom = Mock()

	private final Template yearlinkTemplateTo = Mock()

	private final DayMonthYear datelinkDayMonthYearFrom = DayMonthYear.of(DAY_FROM, MONTH_FROM, YEAR_FROM)

	private final DayMonthYear datelinkDayMonthYearTo = DayMonthYear.of(DAY_TO, MONTH_TO, YEAR_TO)

	private final DayMonthYear monthlinkDayMonthYearFrom = DayMonthYear.of(null, MONTH_FROM, YEAR_FROM)

	private final DayMonthYear monthlinkDayMonthYearTo = DayMonthYear.of(null, MONTH_TO, YEAR_TO)

	private final DayMonthYear yearlinkDayMonthYearFrom = DayMonthYear.of(null, null, YEAR_FROM)

	private final DayMonthYear yearlinkDayMonthYearTo = DayMonthYear.of(null, null, YEAR_TO)

	void setup() {
		templateFilterMock = Mock()
		templateToDayMonthYearParserMock = Mock()
		dayMonthYearRangeProcessor = new DayMonthYearRangeProcessor(templateFilterMock,
				templateToDayMonthYearParserMock)
	}

	void "when single datelink template is found, it is used to populate star and end publication dates"() {
		given:
		Template.Part templatePart = new Template.Part()

		when:
		Range<DayMonthYear> dayMonthYearRange = dayMonthYearRangeProcessor.process(templatePart)

		then:
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.D, TemplateTitle.DATELINK) >> Lists
				.newArrayList(datelinkTemplateFrom)
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.M, TemplateTitle.MONTHLINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.Y, TemplateTitle.YEARLINK) >> Lists.newArrayList()
		1 * templateToDayMonthYearParserMock.parseDayMonthYearCandidate(datelinkTemplateFrom) >> datelinkDayMonthYearFrom
		dayMonthYearRange.from.day == DAY_FROM
		dayMonthYearRange.from.month == MONTH_FROM
		dayMonthYearRange.from.year == YEAR_FROM
		dayMonthYearRange.to == null
		0 * _
	}

	void "when both datelink templates are found, they are used to populate star and end publication dates"() {
		given:
		Template.Part templatePart = new Template.Part()

		when:
		Range<DayMonthYear> dayMonthYearRange = dayMonthYearRangeProcessor.process(templatePart)

		then:
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.D, TemplateTitle.DATELINK) >> Lists.newArrayList(
				datelinkTemplateFrom, datelinkTemplateTo)
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.M, TemplateTitle.MONTHLINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.Y, TemplateTitle.YEARLINK) >> Lists.newArrayList()
		1 * templateToDayMonthYearParserMock.parseDayMonthYearCandidate(datelinkTemplateFrom) >> datelinkDayMonthYearFrom
		1 * templateToDayMonthYearParserMock.parseDayMonthYearCandidate(datelinkTemplateTo) >> datelinkDayMonthYearTo
		dayMonthYearRange.from.day == DAY_FROM
		dayMonthYearRange.from.month == MONTH_FROM
		dayMonthYearRange.from.year == YEAR_FROM
		dayMonthYearRange.to.day == DAY_TO
		dayMonthYearRange.to.month == MONTH_TO
		dayMonthYearRange.to.year == YEAR_TO
		0 * _
	}

	void "when single monthlink template is found, it is used to populate star and end publication dates"() {
		given:
		Template.Part templatePart = new Template.Part()

		when:
		Range<DayMonthYear> dayMonthYearRange = dayMonthYearRangeProcessor.process(templatePart)

		then:
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.D, TemplateTitle.DATELINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.M, TemplateTitle.MONTHLINK) >> Lists
				.newArrayList(monthlinkTemplateFrom)
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.Y, TemplateTitle.YEARLINK) >> Lists.newArrayList()
		1 * templateToDayMonthYearParserMock.parseMonthYearCandidate(monthlinkTemplateFrom) >> monthlinkDayMonthYearFrom
		dayMonthYearRange.from.day == null
		dayMonthYearRange.from.month == MONTH_FROM
		dayMonthYearRange.from.year == YEAR_FROM
		dayMonthYearRange.to == null
		0 * _
	}

	void "when both monthlink templates are found, they are used to populate star and end publication dates"() {
		given:
		Template.Part templatePart = new Template.Part()

		when:
		Range<DayMonthYear> dayMonthYearRange = dayMonthYearRangeProcessor.process(templatePart)

		then:
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.D, TemplateTitle.DATELINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.M, TemplateTitle.MONTHLINK) >> Lists
				.newArrayList(monthlinkTemplateFrom, monthlinkTemplateTo)
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.Y, TemplateTitle.YEARLINK) >> Lists.newArrayList()
		1 * templateToDayMonthYearParserMock.parseMonthYearCandidate(monthlinkTemplateFrom) >> monthlinkDayMonthYearFrom
		1 * templateToDayMonthYearParserMock.parseMonthYearCandidate(monthlinkTemplateTo) >> monthlinkDayMonthYearTo
		dayMonthYearRange.from.day == null
		dayMonthYearRange.from.month == MONTH_FROM
		dayMonthYearRange.from.year == YEAR_FROM
		dayMonthYearRange.to.day == null
		dayMonthYearRange.to.month == MONTH_TO
		dayMonthYearRange.to.year == YEAR_TO
		0 * _
	}

	void "when single yearlink template is found, it is used to populate star and end publication dates"() {
		given:
		Template.Part templatePart = new Template.Part()

		when:
		Range<DayMonthYear> dayMonthYearRange = dayMonthYearRangeProcessor.process(templatePart)

		then:
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.D, TemplateTitle.DATELINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.M, TemplateTitle.MONTHLINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.Y, TemplateTitle.YEARLINK) >> Lists
				.newArrayList(yearlinkTemplateFrom)
		1 * templateToDayMonthYearParserMock.parseYearCandidate(yearlinkTemplateFrom) >> yearlinkDayMonthYearFrom
		dayMonthYearRange.from.day == null
		dayMonthYearRange.from.month == null
		dayMonthYearRange.from.year == YEAR_FROM
		dayMonthYearRange.to == null
		0 * _
	}

	void "when both yearlink templates are found, they are used to populate star and end publication dates"() {
		given:
		Template.Part templatePart = new Template.Part()

		when:
		Range<DayMonthYear> dayMonthYearRange = dayMonthYearRangeProcessor.process(templatePart)

		then:
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.D, TemplateTitle.DATELINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.M, TemplateTitle.MONTHLINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.Y, TemplateTitle.YEARLINK) >> Lists
				.newArrayList(yearlinkTemplateFrom, yearlinkTemplateTo)
		1 * templateToDayMonthYearParserMock.parseYearCandidate(yearlinkTemplateFrom) >> yearlinkDayMonthYearFrom
		1 * templateToDayMonthYearParserMock.parseYearCandidate(yearlinkTemplateTo) >> yearlinkDayMonthYearTo
		dayMonthYearRange.from.day == null
		dayMonthYearRange.from.month == null
		dayMonthYearRange.from.year == YEAR_FROM
		dayMonthYearRange.to.day == null
		dayMonthYearRange.to.month == null
		dayMonthYearRange.to.year == YEAR_TO
		0 * _
	}

	void "when both yearlink templates are found, but could not be parsed, null values are passed"() {
		given:
		Template.Part templatePart = new Template.Part()

		when:
		Range<DayMonthYear> dayMonthYearRange = dayMonthYearRangeProcessor.process(templatePart)

		then:
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.D, TemplateTitle.DATELINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.M, TemplateTitle.MONTHLINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.Y, TemplateTitle.YEARLINK) >> Lists
				.newArrayList(yearlinkTemplateFrom, yearlinkTemplateTo)
		1 * templateToDayMonthYearParserMock.parseYearCandidate(yearlinkTemplateFrom) >> null
		1 * templateToDayMonthYearParserMock.parseYearCandidate(yearlinkTemplateTo) >> null
		dayMonthYearRange.from == null
		dayMonthYearRange.to == null
		0 * _
	}

	void "returns empty range when nothing was found"() {
		given:
		Template.Part templatePart = new Template.Part()

		when:
		Range<DayMonthYear> dayMonthYearRange = dayMonthYearRangeProcessor.process(templatePart)

		then:
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.D, TemplateTitle.DATELINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.M, TemplateTitle.MONTHLINK) >> Lists.newArrayList()
		1 * templateFilterMock.filterByTitle(Lists.newArrayList(), TemplateTitle.Y, TemplateTitle.YEARLINK) >> Lists.newArrayList()
		dayMonthYearRange.from == null
		dayMonthYearRange.to == null
		0 * _
	}

}
