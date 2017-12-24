package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PartToGenderProcessor
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplateParameter
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class IndividualTemplatePartsGenderProcessorTest extends Specification {

	private static final Gender GENDER = Gender.F

	private PartToGenderProcessor partToGenderProcessorMock

	private IndividualTemplatePartsGenderProcessor individualTemplatePartsGenderProcessor

	void setup() {
		partToGenderProcessorMock = Mock()
		individualTemplatePartsGenderProcessor = new IndividualTemplatePartsGenderProcessor(partToGenderProcessorMock)
	}

	void "returns gender from PartToGenderProcessor when gender part was found"() {
		given:
		Template.Part templatePart = new Template.Part(key: IndividualTemplateParameter.GENDER)

		when:
		Gender gender = individualTemplatePartsGenderProcessor.process(Lists.newArrayList(templatePart))

		then:
		1 * partToGenderProcessorMock.process(templatePart) >> GENDER
		0 * _
		gender == GENDER
	}

	void "returns null when no gender part can be found"() {
		given:
		Template.Part templatePart = new Template.Part(key: IndividualTemplateParameter.BORN)

		when:
		Gender gender = individualTemplatePartsGenderProcessor.process(Lists.newArrayList(templatePart))

		then:
		0 * _
		gender == null
	}

}
