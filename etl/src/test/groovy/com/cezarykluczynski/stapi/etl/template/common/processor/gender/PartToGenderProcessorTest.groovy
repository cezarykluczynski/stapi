package com.cezarykluczynski.stapi.etl.template.common.processor.gender

import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

class PartToGenderProcessorTest extends Specification {

	private PartToGenderProcessor partToGenderProcessor

	void setup() {
		partToGenderProcessor = new PartToGenderProcessor()
	}

	void "gets female gender"() {
		when:
		Gender gender = partToGenderProcessor.process(new Template.Part(value: ' Female '))

		then:
		gender == Gender.F
	}

	void "gets male gender"() {
		when:
		Gender gender = partToGenderProcessor.process(new Template.Part(value: ' Male '))

		then:
		gender == Gender.M
	}

	void "return null when gender could not be determined"() {
		when:
		Gender gender = partToGenderProcessor.process(new Template.Part(value: 'err value'))

		then:
		gender == null
	}

}
