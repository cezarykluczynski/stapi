package com.cezarykluczynski.stapi.etl.character.link.relation.service

import com.cezarykluczynski.stapi.util.tool.RandomUtil
import spock.lang.Specification
import spock.lang.Unroll

class CharacterRelationsTemplateParametersDetectorTest extends Specification {

	private static final String UNKNOWN_PARAMETER = 'unknown parameter'

	private CharacterRelationsTemplateParametersDetector characterRelationsTemplateParametersDetector

	void setup() {
		characterRelationsTemplateParametersDetector = new CharacterRelationsTemplateParametersDetector()
	}

	@Unroll('when #templateParameter is passes to isSidebarIndividualPartKey, #result is returned')
	void "when template parameter name is passed to isSidebarIndividualPartKey, boolean flag is returned"() {
		expect:
		characterRelationsTemplateParametersDetector.isSidebarIndividualPartKey(templateParameter) == result

		where:
		templateParameter                                                                                     | result
		RandomUtil.randomItem(CharacterRelationsTemplateParametersDetector.SIDEBAR_INDIVIDUAL_RELATIONS_KEYS) | true
		UNKNOWN_PARAMETER                                                                                     | false
	}

	@Unroll('when #templateParameter is passes to isSidebarHologramPartKey, #result is returned')
	void "when template parameter name is passed to isSidebarHologramPartKey, boolean flag is returned"() {
		expect:
		characterRelationsTemplateParametersDetector.isSidebarHologramPartKey(templateParameter) == result

		where:
		templateParameter                                                                                   | result
		RandomUtil.randomItem(CharacterRelationsTemplateParametersDetector.SIDEBAR_HOLOGRAM_RELATIONS_KEYS) | true
		UNKNOWN_PARAMETER                                                                                   | false
	}

	@Unroll('when #templateParameter is passes to isSidebarFictionalPartKey, #result is returned')
	void "when template parameter name is passed to isSidebarFictionalPartKey, boolean flag is returned"() {
		expect:
		characterRelationsTemplateParametersDetector.isSidebarFictionalPartKey(templateParameter) == result

		where:
		templateParameter                                                                                    | result
		RandomUtil.randomItem(CharacterRelationsTemplateParametersDetector.SIDEBAR_FICTIONAL_RELATIONS_KEYS) | true
		UNKNOWN_PARAMETER                                                                                    | false
	}

}
