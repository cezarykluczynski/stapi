package com.cezarykluczynski.stapi.etl.template.characterbox.processor

import com.cezarykluczynski.stapi.etl.template.characterbox.dto.CharacterboxTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender
import com.cezarykluczynski.stapi.etl.template.common.processor.MaritalStatusProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PartToGenderProcessor
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualHeightProcessor
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualWeightProcessor
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.cezarykluczynski.stapi.util.constant.TemplateName
import com.google.common.collect.Lists
import spock.lang.Specification

class CharacterboxTemplateProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String VALUE = 'VALUE'
	private static final Integer HEIGHT = 183
	private static final Integer WEIGHT = 77
	private static final Gender GENDER = Gender.F
	private static final MaritalStatus MARITAL_STATUS = MaritalStatus.MARRIED

	private TemplateFinder templateFinderMock

	private PartToGenderProcessor partToGenderProcessorMock

	private IndividualHeightProcessor individualHeightProcessorMock

	private IndividualWeightProcessor individualWeightProcessorMock

	private MaritalStatusProcessor maritalStatusProcessorMock

	private CharacterboxTemplateProcessor characterboxTemplateProcessor

	def setup() {
		templateFinderMock = Mock(TemplateFinder)
		partToGenderProcessorMock = Mock(PartToGenderProcessor)
		individualHeightProcessorMock = Mock(IndividualHeightProcessor)
		individualWeightProcessorMock = Mock(IndividualWeightProcessor)
		maritalStatusProcessorMock = Mock(MaritalStatusProcessor)
		characterboxTemplateProcessor = new CharacterboxTemplateProcessor(templateFinderMock, partToGenderProcessorMock,
				individualHeightProcessorMock, individualWeightProcessorMock, maritalStatusProcessorMock)
	}

	def "sets gender from PartToGenderProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: CharacterboxTemplateProcessor.GENDER)
		Template template = createTemplateWithTemplatePart(templatePart)
		Page page = createPageWithTemplate(template)

		when:
		CharacterboxTemplate characterboxTemplate = characterboxTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.CHARACTER_BOX) >> Optional.of(template)
		1 * partToGenderProcessorMock.process(templatePart) >> GENDER
		0 * _
		characterboxTemplate.gender == GENDER
		ReflectionTestUtils.getNumberOfNotNullFields(characterboxTemplate) == 1
	}

	def "sets height from IndividualHeightProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: CharacterboxTemplateProcessor.HEIGHT, value: VALUE)
		Template template = createTemplateWithTemplatePart(templatePart)
		Page page = createPageWithTemplate(template)

		when:
		CharacterboxTemplate characterboxTemplate = characterboxTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.CHARACTER_BOX) >> Optional.of(template)
		1 * individualHeightProcessorMock.process(VALUE) >> HEIGHT
		0 * _
		characterboxTemplate.height == HEIGHT
		ReflectionTestUtils.getNumberOfNotNullFields(characterboxTemplate) == 1
	}

	def "sets weight from IndividualWeightProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: CharacterboxTemplateProcessor.WEIGHT, value: VALUE)
		Template template = createTemplateWithTemplatePart(templatePart)
		Page page = createPageWithTemplate(template)

		when:
		CharacterboxTemplate characterboxTemplate = characterboxTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.CHARACTER_BOX) >> Optional.of(template)
		1 * individualWeightProcessorMock.process(VALUE) >> WEIGHT
		0 * _
		characterboxTemplate.weight == WEIGHT
		ReflectionTestUtils.getNumberOfNotNullFields(characterboxTemplate) == 1
	}

	def "sets marital status from MaritalStatusProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: CharacterboxTemplateProcessor.MARITAL_STATUS, value: VALUE)
		Template template = createTemplateWithTemplatePart(templatePart)
		Page page = createPageWithTemplate(template)

		when:
		CharacterboxTemplate characterboxTemplate = characterboxTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.CHARACTER_BOX) >> Optional.of(template)
		1 * maritalStatusProcessorMock.process(VALUE) >> MARITAL_STATUS
		0 * _
		characterboxTemplate.maritalStatus == MARITAL_STATUS
		ReflectionTestUtils.getNumberOfNotNullFields(characterboxTemplate) == 1
	}

	private static Template createTemplateWithTemplatePart(Template.Part templatePart) {
		return new Template(
				title: TemplateName.CHARACTER_BOX,
				parts: Lists.newArrayList(templatePart)
		)
	}

	private static Page createPageWithTemplate(Template template) {
		return new Page(
				title: TITLE,
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList(
						template
				)
		)
	}

}
