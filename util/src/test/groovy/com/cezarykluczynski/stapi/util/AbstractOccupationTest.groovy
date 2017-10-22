package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil
import spock.lang.Specification

class AbstractOccupationTest extends Specification {

	protected static final String NAME = 'NAME'
	protected static final String UID = 'UID'
	protected static final boolean LEGAL_OCCUPATION = RandomUtil.nextBoolean()
	protected static final boolean MEDICAL_OCCUPATION = RandomUtil.nextBoolean()
	protected static final boolean SCIENTIFIC_OCCUPATION = RandomUtil.nextBoolean()

}
