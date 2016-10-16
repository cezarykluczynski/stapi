package com.cezarykluczynski.stapi.etl.template.common.processor.gender

import com.cezarykluczynski.stapi.etl.template.common.dto.Gender
import com.cezarykluczynski.stapi.wiki.dto.Page
import spock.lang.Specification

class PageToGenderSupplementaryProcessorTest extends Specification {

	private PageToGenderSupplementaryProcessor pageToGenderSupplementaryProcessor

	def setup() {
		pageToGenderSupplementaryProcessor = new PageToGenderSupplementaryProcessor()
	}

	def "gets finding when name is found"() {
		when:
		PageToGenderSupplementaryProcessor.Finding finding = pageToGenderSupplementaryProcessor
				.process(new Page(title: "Maurishka"))

		then:
		finding.found
		finding.gender == Gender.F
	}

	def "gets empty finding when name is not found"() {
		when:
		PageToGenderSupplementaryProcessor.Finding finding = pageToGenderSupplementaryProcessor
				.process(new Page(title: "Brent Spiner"))

		then:
		!finding.found
		finding.gender == null
	}

}
