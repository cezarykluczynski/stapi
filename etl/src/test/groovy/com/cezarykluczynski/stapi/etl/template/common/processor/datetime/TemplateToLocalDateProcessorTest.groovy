package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.util.constants.TemplateNames
import com.cezarykluczynski.stapi.wiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

import java.time.LocalDate
import java.time.Month

class TemplateToLocalDateProcessorTest extends Specification {

	private static final Integer START_YEAR = 2000
	private static final Month START_MONTH = Month.APRIL
	private static final Integer START_DAY = 3

	private TemplateToLocalDateProcessor templateToLocalDateProcessor

	private Template template

	def setup() {
		templateToLocalDateProcessor = new TemplateToLocalDateProcessor()
		template = new Template(
				title: TemplateNames.D,
				parts: Lists.newArrayList(
						new Template.Part(key: "1", value: START_DAY.toString()),
						new Template.Part(key: "2", value: START_MONTH.toString()),
						new Template.Part(key: "3", value: START_YEAR.toString()),
				)
		)
	}

	def "valid template with title 'd' is parsed to LocalDate"() {
		when:
		LocalDate localDate = templateToLocalDateProcessor.process(template)

		then:
		localDate == LocalDate.of(START_YEAR, START_MONTH, START_DAY)
	}

	def "valid template with title 'datelink' is parsed to LocalDate"() {
		given:
		template.title = TemplateNames.DATELINK

		when:
		LocalDate localDate = templateToLocalDateProcessor.process(template)

		then:
		localDate == LocalDate.of(START_YEAR, START_MONTH, START_DAY)
	}

	def "template of different title produces null LocalDate"() {
		when:
		LocalDate localDate = templateToLocalDateProcessor.process(new Template(title: "different template"))

		then:
		localDate == null
	}

	def "invalid day produces null LocalDate"() {
		given:
		template.parts[0].value = "INVALID"

		when:
		LocalDate localDate = templateToLocalDateProcessor.process(template)

		then:
		localDate == null
	}

	def "invalid month produces null LocalDate"() {
		given:
		template.parts[1].value = "INVALID"

		when:
		LocalDate localDate = templateToLocalDateProcessor.process(template)

		then:
		localDate == null
	}

	def "invalid year produces null LocalDate"() {
		given:
		template.parts[2].value = "INVALID"

		when:
		LocalDate localDate = templateToLocalDateProcessor.process(template)

		then:
		localDate == null
	}

}
