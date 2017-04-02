package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.LogicUtil
import spock.lang.Specification

abstract class AbstractOrganizationTest extends Specification {

	protected static final String GUID = 'ABCD0123456789'
	protected static final String NAME = 'NAME'
	protected static final boolean GOVERNMENT = LogicUtil.nextBoolean()
	protected static final boolean INTERGOVERNMENTAL_ORGANIZATION = LogicUtil.nextBoolean()
	protected static final boolean RESEARCH_ORGANIZATION = LogicUtil.nextBoolean()
	protected static final boolean SPORT_ORGANIZATION = LogicUtil.nextBoolean()
	protected static final boolean MEDICAL_ORGANIZATION = LogicUtil.nextBoolean()
	protected static final boolean MILITARY_ORGANIZATION = LogicUtil.nextBoolean()
	protected static final boolean MILITARY_UNIT = LogicUtil.nextBoolean()
	protected static final boolean GOVERNMENT_AGENCY = LogicUtil.nextBoolean()
	protected static final boolean LAW_ENFORCEMENT_AGENCY = LogicUtil.nextBoolean()
	protected static final boolean PRISON_OR_PENAL_COLONY = LogicUtil.nextBoolean()
	protected static final boolean ESTABLISHMENT = LogicUtil.nextBoolean()
	protected static final boolean DS9_ESTABLISHMENT = LogicUtil.nextBoolean()
	protected static final boolean SCHOOL = LogicUtil.nextBoolean()
	protected static final boolean MIRROR = LogicUtil.nextBoolean()
	protected static final boolean ALTERNATE_REALITY = LogicUtil.nextBoolean()

}
