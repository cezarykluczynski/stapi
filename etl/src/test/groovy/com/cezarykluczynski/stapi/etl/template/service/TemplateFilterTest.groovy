package com.cezarykluczynski.stapi.etl.template.service

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class TemplateFilterTest extends Specification {

	private static final String TITLE_1 = 'TITLE_1'
	private static final String TITLE_2 = 'TITLE_2'
	private static final String INVALID_TITLE = 'INVALID_TEMPLATE'

	private TemplateFilter templateFilter

	void setup() {
		templateFilter = new TemplateFilter()
	}

	void "filters templates by title"() {
		given:
		Template template1 = new Template(title: TITLE_1)
		Template template2 = new Template(title: TITLE_2)
		Template templateInvalid = new Template(title: INVALID_TITLE)
		List<Template> templateList = Lists.newArrayList(templateInvalid, template2, template1)

		when:
		List<Template> templateListOutput = templateFilter.filterByTitle(templateList, TITLE_1, TITLE_2)

		then:
		templateListOutput.size() == 2
		templateListOutput.contains template1
		templateListOutput.contains template2
	}

}
