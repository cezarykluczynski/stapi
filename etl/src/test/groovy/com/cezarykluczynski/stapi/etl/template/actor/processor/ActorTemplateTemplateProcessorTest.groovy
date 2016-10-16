package com.cezarykluczynski.stapi.etl.template.actor.processor

import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.Gender
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PartToGenderProcessor
import com.cezarykluczynski.stapi.wiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification


class ActorTemplateTemplateProcessorTest extends Specification {

	private static final String NAME = 'NAME'
	private static final String BIRTH_NAME = 'BIRTH_NAME'
	private static final Gender GENDER = Gender.F

	private PartToGenderProcessor partToGenderProcessorMock

	private ActorTemplateTemplateProcessor actorTemplateTemplateProcessor

	def setup() {
		partToGenderProcessorMock = Mock(PartToGenderProcessor)
		actorTemplateTemplateProcessor = new ActorTemplateTemplateProcessor(partToGenderProcessorMock)
	}

	def "valid template is parsed"() {
		given:
		Template.Part genderPart = new Template.Part(key: ActorTemplateTemplateProcessor.GENDER)
		Template template = new Template(parts: Lists.newArrayList(
				new Template.Part(key: ActorTemplateTemplateProcessor.NAME, value: NAME),
				new Template.Part(key: ActorTemplateTemplateProcessor.BIRTH_NAME, value: BIRTH_NAME),
				genderPart
		))

		when:
		ActorTemplate actorTemplate = actorTemplateTemplateProcessor.process(template)

		then: 'gender parsing is delegated'
		1 * partToGenderProcessorMock.process(genderPart) >> GENDER

		then: 'all values are parsed'
		actorTemplate.name == NAME
		actorTemplate.birthName == BIRTH_NAME
		actorTemplate.gender == GENDER
	}

}
