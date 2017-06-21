package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil

class AbstractVideoReleaseTest extends AbstractTest {

	protected static final String UID = 'ABCD0123456789'
	protected static final String TITLE = 'TITLE'
	protected static final boolean AMAZON_DIGITAL_RELEASE = RandomUtil.nextBoolean()
	protected static final boolean DAILYMOTION_DIGITAL_RELEASE = RandomUtil.nextBoolean()
	protected static final boolean GOOGLE_PLAY_DIGITAL_RELEASE = RandomUtil.nextBoolean()
	protected static final boolean I_TUNES_DIGITAL_RELEASE = RandomUtil.nextBoolean()
	protected static final boolean ULTRA_VIOLET_DIGITAL_RELEASE = RandomUtil.nextBoolean()
	protected static final boolean VIMEO_DIGITAL_RELEASE = RandomUtil.nextBoolean()
	protected static final boolean VUDU_DIGITAL_RELEASE = RandomUtil.nextBoolean()
	protected static final boolean XBOX_SMART_GLASS_DIGITAL = RandomUtil.nextBoolean()
	protected static final boolean YOU_TUBE_DIGITAL_RELEASE = RandomUtil.nextBoolean()
	protected static final boolean NETFLIX_DIGITAL_RELEASE = RandomUtil.nextBoolean()

}
