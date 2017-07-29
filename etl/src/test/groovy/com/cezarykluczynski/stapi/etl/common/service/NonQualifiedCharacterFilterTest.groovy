package com.cezarykluczynski.stapi.etl.common.service

import com.cezarykluczynski.stapi.util.tool.RandomUtil
import spock.lang.Specification

class NonQualifiedCharacterFilterTest extends Specification {

	private NonQualifiedCharacterFilter nonQualifiedCharacterFilter

	void setup() {
		nonQualifiedCharacterFilter = new NonQualifiedCharacterFilter()
	}

	void "returns true when given string contains part to exclude"() {
		expect:
		nonQualifiedCharacterFilter.shouldBeFilteredOut("blah ${RandomUtil.randomItem(NonQualifiedCharacterFilter.REDIRECTS_TO_LISTS_PARTS)} blah")
	}

	void "returns true when given string is on list of string to exclude"() {
		expect:
		nonQualifiedCharacterFilter.shouldBeFilteredOut(RandomUtil.randomItem(NonQualifiedCharacterFilter.REDIRECTS_TO_LISTS_EXACT))
	}

	void "returns false for regular character"() {
		expect:
		!nonQualifiedCharacterFilter.shouldBeFilteredOut('Jean-Luc Picard')
	}

}
