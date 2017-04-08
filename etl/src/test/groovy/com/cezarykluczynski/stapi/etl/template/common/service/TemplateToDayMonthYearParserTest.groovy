package com.cezarykluczynski.stapi.etl.template.common.service

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYear
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DatelinkTemplateToDayMonthYearProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.MonthlinkTemplateToMonthYearProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.YearlinkToYearProcessor
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

class TemplateToDayMonthYearParserTest extends Specification {

	private DatelinkTemplateToDayMonthYearProcessor datelinkTemplateToDayMonthYearProcessorMock

	private MonthlinkTemplateToMonthYearProcessor monthlinkTemplateToMonthYearProcessorMock

	private YearlinkToYearProcessor yearlinkToYearProcessorMock

	private TemplateToDayMonthYearParser templateToDayMonthYearParser

	void setup() {
		datelinkTemplateToDayMonthYearProcessorMock = Mock()
		monthlinkTemplateToMonthYearProcessorMock = Mock()
		yearlinkToYearProcessorMock = Mock()
		templateToDayMonthYearParser = new TemplateToDayMonthYearParser(datelinkTemplateToDayMonthYearProcessorMock,
				monthlinkTemplateToMonthYearProcessorMock, yearlinkToYearProcessorMock)
	}

	void "passes day month year template candidate to DatelinkTemplateToDayMonthYearProcessor"() {
		given:
		DayMonthYear dayMonthYear = Mock()
		Template template = Mock()

		when:
		DayMonthYear dayMonthYearOutput = templateToDayMonthYearParser.parseDayMonthYearCandidate(template)

		then:
		1 * datelinkTemplateToDayMonthYearProcessorMock.process(template) >> dayMonthYear
		0 * _
		dayMonthYearOutput == dayMonthYear
	}

	void "passes month year template candidate to MonthlinkTemplateToMonthYearProcessor"() {
		given:
		DayMonthYear dayMonthYear = Mock()
		Template template = Mock()

		when:
		DayMonthYear dayMonthYearOutput = templateToDayMonthYearParser.parseMonthYearCandidate(template)

		then:
		1 * monthlinkTemplateToMonthYearProcessorMock.process(template) >> dayMonthYear
		0 * _
		dayMonthYearOutput == dayMonthYear
	}

	void "passes year template candidate to YearlinkToYearProcessor"() {
		given:
		Integer year = 1
		Template template = Mock()

		when:
		DayMonthYear dayMonthYearOutput = templateToDayMonthYearParser.parseYearCandidate(template)

		then:
		1 * yearlinkToYearProcessorMock.process(template) >> year
		0 * _
		dayMonthYearOutput.year == year
	}

}
