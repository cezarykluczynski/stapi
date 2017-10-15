package com.cezarykluczynski.stapi.etl.common.processor

import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class TemplateTitlesExtractingProcessorTest extends Specification {

	private static final String TEMPLATE_TITLE_1 = 'TEMPLATE_TITLE 1'
	private static final String TEMPLATE_TITLE_2 = 'TEMPLATE_TITLE_2'

	private TemplateTitlesExtractingProcessor templateTitlesExtractingProcessor

	void setup() {
		templateTitlesExtractingProcessor = new TemplateTitlesExtractingProcessor()
	}

	void "extracts titles from TemplateHeader list"() {
		when:
		List<String> templateTitleList = templateTitlesExtractingProcessor.process(Lists.newArrayList(
				new Template(title: TEMPLATE_TITLE_1),
				new Template(title: TEMPLATE_TITLE_2)
		))

		then:
		templateTitleList.size() == 2
		templateTitleList[0] == TEMPLATE_TITLE_1
		templateTitleList[1] == TEMPLATE_TITLE_2
	}

}
