package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil

class AbstractOccupationTest extends AbstractTest {

	protected static final String NAME = 'NAME'
	protected static final String UID = 'UID'
	protected static final boolean LEGAL_OCCUPATION = RandomUtil.nextBoolean()
	protected static final boolean MEDICAL_OCCUPATION = RandomUtil.nextBoolean()
	protected static final boolean SCIENTIFIC_OCCUPATION = RandomUtil.nextBoolean()

}
