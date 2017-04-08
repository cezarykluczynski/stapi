package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYearCandidate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

import java.time.LocalDate
import java.time.Month

class DatelinkTemplateToLocalDateProcessorTest extends Specification {

	private DatelinkTemplateToDayMonthYearCandiateProcessor datelinkTemplateToDayMonthYearCandiateProcessorMock

	private DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessorMock

	private DatelinkTemplateToLocalDateProcessor templateToLocalDateProcessor

	void setup() {
		datelinkTemplateToDayMonthYearCandiateProcessorMock = Mock()
		dayMonthYearCandidateToLocalDateProcessorMock = Mock()
		templateToLocalDateProcessor = new DatelinkTemplateToLocalDateProcessor(datelinkTemplateToDayMonthYearCandiateProcessorMock,
				dayMonthYearCandidateToLocalDateProcessorMock)
	}

	void "passes result of DatelinkTemplateToDayMonthYearCandiateProcessor to DayMonthYearProcessor"() {
		given:

		LocalDate localDate = LocalDate.of(2000, Month.APRIL, 3)
		DayMonthYearCandidate dayMonthYearCandidate = Mock()
		Template template = Mock()

		when:
		LocalDate localDateOutput = templateToLocalDateProcessor.process(template)

		then:
		1 * datelinkTemplateToDayMonthYearCandiateProcessorMock.process(template) >> dayMonthYearCandidate
		1 * dayMonthYearCandidateToLocalDateProcessorMock.process(dayMonthYearCandidate) >> localDate
		localDateOutput == localDate
	}

}
