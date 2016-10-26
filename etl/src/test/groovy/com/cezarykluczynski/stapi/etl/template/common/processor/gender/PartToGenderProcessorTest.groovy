package com.cezarykluczynski.stapi.etl.template.common.processor.gender

import com.cezarykluczynski.stapi.etl.template.common.dto.Gender
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import spock.lang.Specification

class PartToGenderProcessorTest extends Specification {

	private PartToGenderProcessor partToGenderProcessor

	def setup() {
		partToGenderProcessor = new PartToGenderProcessor()
	}

	def "gets female gender"() {
		when:
		Gender gender = partToGenderProcessor.process(new Template.Part(value: " Female "))

		then:
		gender == Gender.F
	}

	def "gets male gender"() {
		when:
		Gender gender = partToGenderProcessor.process(new Template.Part(value: " Male "))

		then:
		gender == Gender.M
	}

	def "return null when gender could not be determined"() {
		when:
		Gender gender = partToGenderProcessor.process(new Template.Part(value: "err value"))

		then:
		gender == null
	}

}
