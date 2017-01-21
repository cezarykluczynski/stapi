package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.google.common.collect.Lists
import spock.lang.Specification

import java.time.LocalDate

class RawDatelinkExtractingProcessorTest extends Specification {

	private RawDatelinkExtractingProcessor rawDatelinkExtractingProcessor

	void setup() {
		rawDatelinkExtractingProcessor = new RawDatelinkExtractingProcessor(new DayMonthYearProcessor())
	}

	void "extracts valid dates"() {
		expect:
		rawDatelinkExtractingProcessor.process('blah {{datelink|3|January|1965}} blah') == Lists.newArrayList(LocalDate.of(1965, 1, 3))
		rawDatelinkExtractingProcessor.process('blah {{d|9|February|1969}} blah') == Lists.newArrayList(LocalDate.of(1969, 2, 9))
		rawDatelinkExtractingProcessor.process('blah {{datelink|4|March|1975}} blah') == Lists.newArrayList(LocalDate.of(1975, 3, 4))
		rawDatelinkExtractingProcessor.process('blah {{d|1|April|2010}} blah') == Lists.newArrayList(LocalDate.of(2010, 4, 1))
		rawDatelinkExtractingProcessor.process('blah {{datelink|7|May|1955}} blah') == Lists.newArrayList(LocalDate.of(1955, 5, 7))
		rawDatelinkExtractingProcessor.process('blah {{d|22|June|1997}} blah') == Lists.newArrayList(LocalDate.of(1997, 6, 22))
		rawDatelinkExtractingProcessor.process('blah {{datelink|18|July|1982}} blah') == Lists.newArrayList(LocalDate.of(1982, 7, 18))
		rawDatelinkExtractingProcessor.process('blah {{d|15|August|1986}} blah') == Lists.newArrayList(LocalDate.of(1986, 8, 15))
		rawDatelinkExtractingProcessor.process('blah {{datelink|30|September|1979}} blah') == Lists.newArrayList(LocalDate.of(1979, 9, 30))
		rawDatelinkExtractingProcessor.process('blah {{d|25|October|1952}} blah') == Lists.newArrayList(LocalDate.of(1952, 10, 25))
		rawDatelinkExtractingProcessor.process('blah {{datelink|10|November|1995}} blah') == Lists.newArrayList(LocalDate.of(1995, 11, 10))
		rawDatelinkExtractingProcessor.process('blah {{d|6|December|2016}} blah') == Lists.newArrayList(LocalDate.of(2016, 12, 6))
		rawDatelinkExtractingProcessor.process('{{datelink|3|January|1965}} {{d|6|December|2016}}') == Lists.newArrayList(LocalDate.of(1965, 1, 3),
				LocalDate.of(2016, 12, 6))
	}

	void "does not extract invalid dates"() {
		expect:
		rawDatelinkExtractingProcessor.process('blah blah').empty
		rawDatelinkExtractingProcessor.process('blah {{datelink|3|January}} blah').empty
		rawDatelinkExtractingProcessor.process('blah {{datelink|3|January|999}} blah').empty
	}

}
