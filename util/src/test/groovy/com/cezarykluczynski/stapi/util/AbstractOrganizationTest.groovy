package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.RandomUtil

abstract class AbstractOrganizationTest extends AbstractTest {

	protected static final String UID = 'ABCD0123456789'
	protected static final String NAME = 'NAME'
	protected static final boolean GOVERNMENT = RandomUtil.nextBoolean()
	protected static final boolean INTERGOVERNMENTAL_ORGANIZATION = RandomUtil.nextBoolean()
	protected static final boolean RESEARCH_ORGANIZATION = RandomUtil.nextBoolean()
	protected static final boolean SPORT_ORGANIZATION = RandomUtil.nextBoolean()
	protected static final boolean MEDICAL_ORGANIZATION = RandomUtil.nextBoolean()
	protected static final boolean MILITARY_ORGANIZATION = RandomUtil.nextBoolean()
	protected static final boolean MILITARY_UNIT = RandomUtil.nextBoolean()
	protected static final boolean GOVERNMENT_AGENCY = RandomUtil.nextBoolean()
	protected static final boolean LAW_ENFORCEMENT_AGENCY = RandomUtil.nextBoolean()
	protected static final boolean PRISON_OR_PENAL_COLONY = RandomUtil.nextBoolean()
	protected static final boolean MIRROR = RandomUtil.nextBoolean()
	protected static final boolean ALTERNATE_REALITY = RandomUtil.nextBoolean()

}
