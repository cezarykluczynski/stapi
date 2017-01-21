package com.cezarykluczynski.stapi.util.tool

import spock.lang.Specification

class LogicUtilTest extends Specification {

	private static final Object NOT_NULL = new Object()

	void "xor null is handled properly"() {
		expect:
		LogicUtil.xorNull(NOT_NULL, null)
		LogicUtil.xorNull(null, NOT_NULL)
		!LogicUtil.xorNull(NOT_NULL, NOT_NULL)
		!LogicUtil.xorNull(null, null)
	}

}
