package com.cezarykluczynski.stapi.etl.character.creation.processor

import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.common.entity.Gender as EntityGender
import com.cezarykluczynski.stapi.etl.template.common.dto.Gender as EtlGender
import spock.lang.Specification

class CharacterIndividualTemplateProcessorTest extends Specification {

	private static final EtlGender ETL_GENDER = EtlGender.F
	private static final EntityGender ENTITY_GENDER = EntityGender.F

	private CharacterIndividualTemplateProcessor characterIndividualTemplateProcessor

	def setup() {
		characterIndividualTemplateProcessor = new CharacterIndividualTemplateProcessor()
	}

	def "converts IndividualTemplate to Character"() {
		given:
		IndividualTemplate individualTemplate = new IndividualTemplate(
				gender: ETL_GENDER
		)

		when:
		Character character = characterIndividualTemplateProcessor.process(individualTemplate)

		then:
		character.gender == ENTITY_GENDER
	}

}
