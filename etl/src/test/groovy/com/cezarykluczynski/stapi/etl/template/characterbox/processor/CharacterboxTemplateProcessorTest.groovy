package com.cezarykluczynski.stapi.etl.template.characterbox.processor

import com.cezarykluczynski.stapi.etl.template.characterbox.dto.CharacterboxTemplate
import com.cezarykluczynski.stapi.etl.template.characterbox.dto.CharacterboxTemplateParameter
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender
import com.cezarykluczynski.stapi.etl.template.common.processor.MaritalStatusProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PartToGenderProcessor
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualLifeBoundaryDTO
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualHeightProcessor
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualLifeBoundaryProcessor
import com.cezarykluczynski.stapi.etl.template.individual.processor.IndividualWeightProcessor
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class CharacterboxTemplateProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String VALUE = 'VALUE'
	private static final Integer HEIGHT = 183
	private static final Integer WEIGHT = 77
	private static final Integer YEAR = 1970
	private static final Integer MONTH = 10
	private static final Integer DAY = 7
	private static final Gender GENDER = Gender.F
	private static final MaritalStatus MARITAL_STATUS = MaritalStatus.MARRIED

	private TemplateFinder templateFinderMock

	private PartToGenderProcessor partToGenderProcessorMock

	private IndividualHeightProcessor individualHeightProcessorMock

	private IndividualWeightProcessor individualWeightProcessorMock

	private MaritalStatusProcessor maritalStatusProcessorMock

	private IndividualLifeBoundaryProcessor individualLifeBoundaryProcessorMock

	private CharacterboxTemplateProcessor characterboxTemplateProcessor

	void setup() {
		templateFinderMock = Mock()
		partToGenderProcessorMock = Mock()
		individualHeightProcessorMock = Mock()
		individualWeightProcessorMock = Mock()
		individualLifeBoundaryProcessorMock = Mock()
		maritalStatusProcessorMock = Mock()
		characterboxTemplateProcessor = new CharacterboxTemplateProcessor(templateFinderMock, partToGenderProcessorMock,
				individualHeightProcessorMock, individualWeightProcessorMock, maritalStatusProcessorMock, individualLifeBoundaryProcessorMock)
	}

	void "sets gender from PartToGenderProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: CharacterboxTemplateParameter.GENDER)
		Template template = createTemplateWithTemplatePart(templatePart)
		Page page = createPageWithTemplate(template)

		when:
		CharacterboxTemplate characterboxTemplate = characterboxTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.CHARACTER_BOX) >> Optional.of(template)
		1 * partToGenderProcessorMock.process(templatePart) >> GENDER
		0 * _
		characterboxTemplate.gender == GENDER
		ReflectionTestUtils.getNumberOfNotNullFields(characterboxTemplate) == 1
	}

	void "sets height from IndividualHeightProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: CharacterboxTemplateParameter.HEIGHT, value: VALUE)
		Template template = createTemplateWithTemplatePart(templatePart)
		Page page = createPageWithTemplate(template)

		when:
		CharacterboxTemplate characterboxTemplate = characterboxTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.CHARACTER_BOX) >> Optional.of(template)
		1 * individualHeightProcessorMock.process(VALUE) >> HEIGHT
		0 * _
		characterboxTemplate.height == HEIGHT
		ReflectionTestUtils.getNumberOfNotNullFields(characterboxTemplate) == 1
	}

	void "sets weight from IndividualWeightProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: CharacterboxTemplateParameter.WEIGHT, value: VALUE)
		Template template = createTemplateWithTemplatePart(templatePart)
		Page page = createPageWithTemplate(template)

		when:
		CharacterboxTemplate characterboxTemplate = characterboxTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.CHARACTER_BOX) >> Optional.of(template)
		1 * individualWeightProcessorMock.process(VALUE) >> WEIGHT
		0 * _
		characterboxTemplate.weight == WEIGHT
		ReflectionTestUtils.getNumberOfNotNullFields(characterboxTemplate) == 1
	}

	void "sets marital status from MaritalStatusProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(key: CharacterboxTemplateParameter.MARITAL_STATUS, value: VALUE)
		Template template = createTemplateWithTemplatePart(templatePart)
		Page page = createPageWithTemplate(template)

		when:
		CharacterboxTemplate characterboxTemplate = characterboxTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.CHARACTER_BOX) >> Optional.of(template)
		1 * maritalStatusProcessorMock.process(VALUE) >> MARITAL_STATUS
		0 * _
		characterboxTemplate.maritalStatus == MARITAL_STATUS
		ReflectionTestUtils.getNumberOfNotNullFields(characterboxTemplate) == 1
	}

	void "sets birth values from IndividualLifeBoundaryProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: CharacterboxTemplateParameter.BORN,
				value: VALUE)
		IndividualLifeBoundaryDTO individualLifeBoundaryDTO = new IndividualLifeBoundaryDTO(
				year: YEAR,
				month: MONTH,
				day: DAY)
		Template template = createTemplateWithTemplatePart(templatePart)
		Page page = createPageWithTemplate(template)

		when:
		CharacterboxTemplate characterboxTemplate = characterboxTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.CHARACTER_BOX) >> Optional.of(template)
		1 * individualLifeBoundaryProcessorMock.process(VALUE) >> individualLifeBoundaryDTO
		0 * _
		characterboxTemplate.yearOfBirth == YEAR
		characterboxTemplate.monthOfBirth == MONTH
		characterboxTemplate.dayOfBirth == DAY
		ReflectionTestUtils.getNumberOfNotNullFields(characterboxTemplate) == 3
	}

	void "sets death values from IndividualLifeBoundaryProcessor"() {
		given:
		Template.Part templatePart = new Template.Part(
				key: CharacterboxTemplateParameter.DIED,
				value: VALUE)
		IndividualLifeBoundaryDTO individualLifeBoundaryDTO = new IndividualLifeBoundaryDTO(
				year: YEAR,
				month: MONTH,
				day: DAY)
		Template template = createTemplateWithTemplatePart(templatePart)
		Page page = createPageWithTemplate(template)

		when:
		CharacterboxTemplate characterboxTemplate = characterboxTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.CHARACTER_BOX) >> Optional.of(template)
		1 * individualLifeBoundaryProcessorMock.process(VALUE) >> individualLifeBoundaryDTO
		0 * _
		characterboxTemplate.yearOfDeath == YEAR
		characterboxTemplate.monthOfDeath == MONTH
		characterboxTemplate.dayOfDeath == DAY
		ReflectionTestUtils.getNumberOfNotNullFields(characterboxTemplate) == 3
	}

	void "returns null when Characterbox template is not found"() {
		given:
		Template template = createTemplateWithTemplatePart(new Template.Part())
		Page page = createPageWithTemplate(template)

		when:
		CharacterboxTemplate characterboxTemplate = characterboxTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateTitle.CHARACTER_BOX) >> Optional.empty()
		0 * _
		characterboxTemplate == null
	}

	private static Template createTemplateWithTemplatePart(Template.Part templatePart) {
		new Template(
				title: TemplateTitle.CHARACTER_BOX,
				parts: Lists.newArrayList(templatePart)
		)
	}

	private static Page createPageWithTemplate(Template template) {
		new Page(
				title: TITLE,
				categories: Lists.newArrayList(),
				templates: Lists.newArrayList(
						template
				)
		)
	}

}
