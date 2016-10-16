package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.etl.template.common.dto.Gender
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PartToGenderProcessor
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate
import com.cezarykluczynski.stapi.util.constants.TemplateNames
import com.cezarykluczynski.stapi.wiki.dto.Page
import com.cezarykluczynski.stapi.wiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class IndividualTemplatePageProcessorTest extends Specification {

	private static final Gender GENDER = Gender.F

	private PartToGenderProcessor partToGenderProcessorMock

	private IndividualTemplatePageProcessor individualTemplatePageProcessor

	def setup() {
		partToGenderProcessorMock = Mock(PartToGenderProcessor)
		individualTemplatePageProcessor = new IndividualTemplatePageProcessor(partToGenderProcessorMock)
	}

	def "missing template results in null SeriesTemplate"() {
		given:
		Page page = new Page()

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		individualTemplate == null
	}

	def "sets gender from PartToGenderProcessor"() {
		given:
		Template.Part genderTemplatePart = new Template.Part(key: IndividualTemplatePageProcessor.GENDER)
		Page page = new Page(templates: Lists.newArrayList(
				new Template(title: TemplateNames.SIDEBAR_INDIVIDUAL, parts: Lists.newArrayList(
						genderTemplatePart
				))
		))

		when:
		IndividualTemplate individualTemplate = individualTemplatePageProcessor.process(page)

		then:
		1 * partToGenderProcessorMock.process(genderTemplatePart) >> GENDER
		individualTemplate.gender == GENDER
	}

}
