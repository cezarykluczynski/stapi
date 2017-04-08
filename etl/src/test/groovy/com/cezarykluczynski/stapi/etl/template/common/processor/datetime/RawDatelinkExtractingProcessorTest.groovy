package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYearCandidate
import spock.lang.Specification

class RawDatelinkExtractingProcessorTest extends Specification {

	private DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessorMock

	private RawDatelinkExtractingProcessor rawDatelinkExtractingProcessor

	void setup() {
		dayMonthYearCandidateToLocalDateProcessorMock = Mock()
		rawDatelinkExtractingProcessor = new RawDatelinkExtractingProcessor(dayMonthYearCandidateToLocalDateProcessorMock)
	}

	void "extracts valid dates"() {
		given:
		dayMonthYearCandidateToLocalDateProcessorMock.process(_ as DayMonthYearCandidate) >> { DayMonthYearCandidate dayMonthYearCandidate ->
			assert dayMonthYearCandidate.day == day
			assert dayMonthYearCandidate.month == month
			assert dayMonthYearCandidate.year == year
		}

		expect:
		rawDatelinkExtractingProcessor.process(wikitext)

		where:
		wikitext                                   | day  | month       | year
		'blah {{datelink|3|January|1965}} blah'    | '3'  | 'January'   | '1965'
		'blah {{d|9|February|1969}} blah'          | '9'  | 'February'  | '1969'
		'blah {{datelink|4|March|1975}} blah'      | '4'  | 'March'     | '1975'
		'blah {{d|1|April|2010}} blah'             | '1'  | 'April'     | '2010'
		'blah {{datelink|7|May|1955}} blah'        | '7'  | 'May'       | '1955'
		'blah {{d|22|June|1997}} blah'             | '22' | 'June'      | '1997'
		'blah {{datelink|18|July|1982}} blah'      | '18' | 'July'      | '1982'
		'blah {{d|15|August|1986}} blah'           | '15' | 'August'    | '1986'
		'blah {{datelink|30|September|1979}} blah' | '30' | 'September' | '1979'
		'blah {{d|25|October|1952}} blah'          | '25' | 'October'   | '1952'
		'blah {{datelink|10|November|1995}} blah'  | '10' | 'November'  | '1995'
		'blah {{d|6|December|2016}} blah'          | '6'  | 'December'  | '2016'
	}

	void "extracts multiple dates"() {
		when:
		rawDatelinkExtractingProcessor.process('{{datelink|3|January|1965}} {{d|6|December|2016}}')

		then:
		1 * dayMonthYearCandidateToLocalDateProcessorMock.process(_ as DayMonthYearCandidate) >> { DayMonthYearCandidate dayMonthYearCandidate ->
			assert dayMonthYearCandidate.day == '3'
			assert dayMonthYearCandidate.month == 'January'
			assert dayMonthYearCandidate.year == '1965'
		}
		1 * dayMonthYearCandidateToLocalDateProcessorMock.process(_ as DayMonthYearCandidate) >> { DayMonthYearCandidate dayMonthYearCandidate ->
			assert dayMonthYearCandidate.day == '6'
			assert dayMonthYearCandidate.month == 'December'
			assert dayMonthYearCandidate.year == '2016'
		}
		0 * _
	}

	void "does not extract invalid dates"() {
		expect:
		rawDatelinkExtractingProcessor.process('blah blah').empty
		rawDatelinkExtractingProcessor.process('blah {{datelink|3|January}} blah').empty
		rawDatelinkExtractingProcessor.process('blah {{datelink|3|January|999}} blah').empty
	}

}
