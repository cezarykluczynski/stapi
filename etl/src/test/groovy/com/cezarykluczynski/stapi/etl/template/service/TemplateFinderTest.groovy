package com.cezarykluczynski.stapi.etl.template.service

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class TemplateFinderTest extends Specification {

	private static final String TITLE_1 = 'TITLE_1'
	private static final String TITLE_2 = 'TITLE_2'
	private static final String INVALID_TITLE = 'INVALID_TEMPLATE'

	private TemplateFinder templateFinder

	def setup() {
		templateFinder = new TemplateFinder()
	}

	def "finds first template by name"() {
		given:
		Template template1title1 = new Template(title: TITLE_1)
		Template template1title2 = new Template(title: TITLE_1)
		Page page = new Page(templates: Lists.newArrayList(template1title1, template1title2))

		when:
		Optional<Template> templateOptional = templateFinder.findTemplate(page, TITLE_1)

		then:
		templateOptional.get() == template1title1
	}

	def "finds first template by multiple names"() {
		Template template1 = new Template(title: TITLE_1)
		Template template2 = new Template(title: TITLE_2)
		Template templateInvalid = new Template(title: INVALID_TITLE)
		Page page = new Page(templates: Lists.newArrayList(templateInvalid, template2, template1))

		when:
		Optional<Template> templateOptional = templateFinder.findTemplate(page, TITLE_1, TITLE_2)

		then:
		templateOptional.get() == template2
	}

	def "returns empty optional is nothing is found"() {
		Page page = new Page()

		when:
		Optional<Template> templateOptional = templateFinder.findTemplate(page, TITLE_1, TITLE_2)

		then:
		!templateOptional.isPresent()
	}

}
