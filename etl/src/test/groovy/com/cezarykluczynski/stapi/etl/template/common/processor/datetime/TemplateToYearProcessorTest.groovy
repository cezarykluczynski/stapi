package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.util.constant.TemplateName
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class TemplateToYearProcessorTest extends Specification {

	private static final Integer YEAR = 2000

	private TemplateToYearProcessor templateToYearProcessor

	private Template template

	void setup() {
		templateToYearProcessor = new TemplateToYearProcessor()
		template = new Template(
				title: TemplateName.Y,
				parts: Lists.newArrayList(
						new Template.Part(key: '1', value: YEAR),
				)
		)
	}

	void "valid template with title 'y' is parsed to year"() {
		when:
		Integer year = templateToYearProcessor.process(template)

		then:
		year == YEAR
	}

	void "valid template with title 'yearlink' is parsed to year"() {
		given:
		template.title = TemplateName.YEARLINK

		when:
		Integer year = templateToYearProcessor.process(template)

		then:
		year == YEAR
	}

	void "template of different title produces null year"() {
		when:
		Integer year = templateToYearProcessor.process(new Template(title: 'different template'))

		then:
		year == null
	}

	void "when key 1 does not exists, null year is returned"() {
		given:
		template.parts.clear()

		when:
		Integer year = templateToYearProcessor.process(template)

		then:
		year == null
	}

	void "invalid value produces null year"() {
		given:
		template.parts[0].value = 'INVALID'

		when:
		Integer year = templateToYearProcessor.process(template)

		then:
		year == null
	}

}
