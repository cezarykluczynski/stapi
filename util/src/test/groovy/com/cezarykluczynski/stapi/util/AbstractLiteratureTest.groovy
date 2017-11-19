package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil

class AbstractLiteratureTest extends AbstractTest {

	protected static final String UID = 'ABCD0123456789'
	protected static final String TITLE = 'TITLE'
	protected static final boolean EARTHLY_ORIGIN = RandomUtil.nextBoolean()
	protected static final boolean SHAKESPEAREAN_WORK = RandomUtil.nextBoolean()
	protected static final boolean REPORT = RandomUtil.nextBoolean()
	protected static final boolean SCIENTIFIC_LITERATURE = RandomUtil.nextBoolean()
	protected static final boolean TECHNICAL_MANUAL = RandomUtil.nextBoolean()
	protected static final boolean RELIGIOUS_LITERATURE = RandomUtil.nextBoolean()

}
