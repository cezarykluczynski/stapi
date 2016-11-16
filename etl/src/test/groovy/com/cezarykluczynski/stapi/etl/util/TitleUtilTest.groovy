package com.cezarykluczynski.stapi.etl.util

import spock.lang.Specification

class TitleUtilTest extends Specification {

	def "clears title"() {
		expect:
		TitleUtil.getNameFromTitle('Title') == 'Title'
		TitleUtil.getNameFromTitle(' Title') == 'Title'
		TitleUtil.getNameFromTitle('Title ') == 'Title'
		TitleUtil.getNameFromTitle('Title (civilian)') == 'Title'
		TitleUtil.getNameFromTitle(' Title (civilian)') == 'Title'
		TitleUtil.getNameFromTitle(' Title (civilian) ') == 'Title'
	}

}
