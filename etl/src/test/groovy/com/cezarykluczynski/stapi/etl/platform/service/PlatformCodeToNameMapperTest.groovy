package com.cezarykluczynski.stapi.etl.platform.service

import spock.lang.Specification

class PlatformCodeToNameMapperTest extends Specification {

	private PlatformCodeToNameMapper platformCodeToNameMapper

	void setup() {
		platformCodeToNameMapper = new PlatformCodeToNameMapper()
	}

	void "provides name for known code"() {
		expect:
		platformCodeToNameMapper.map('gamegear') == 'Sega Game Gear'
	}

	void "provides null for unknown code"() {
		expect:
		platformCodeToNameMapper.map('mainframe') == null
	}

}
