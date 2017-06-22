package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil

import java.time.LocalDate

class AbstractVideoReleaseTest extends AbstractTest {

	protected static final String UID = 'ABCD0123456789'
	protected static final String TITLE = 'TITLE'
	protected static final LocalDate REGION_FREE_RELEASE_DATE = LocalDate.of(2001, 01, 01)
	protected static final LocalDate REGION1_A_RELEASE_DATE = LocalDate.of(2002, 02, 02)
	protected static final LocalDate REGION1_SLIMLINE_RELEASE_DATE = LocalDate.of(2003, 03, 03)
	protected static final LocalDate REGION2_A_RELEASE_DATE = LocalDate.of(2004, 04, 04)
	protected static final LocalDate REGION2_SLIMLINE_RELEASE_DATE = LocalDate.of(2005, 05, 05)
	protected static final LocalDate REGION4_A_RELEASE_DATE = LocalDate.of(2006, 06, 06)
	protected static final LocalDate REGION4_SLIMLINE_RELEASE_DATE = LocalDate.of(2007, 07, 07)
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
