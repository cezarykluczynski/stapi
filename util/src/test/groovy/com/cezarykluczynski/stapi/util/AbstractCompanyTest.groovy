package com.cezarykluczynski.stapi.util

import com.cezarykluczynski.stapi.util.tool.LogicUtil
import spock.lang.Specification

abstract class AbstractCompanyTest extends Specification {

	protected static final String GUID = 'ABCD0123456789'
	protected static final String NAME = 'NAME'
	protected static final boolean BROADCASTER = LogicUtil.nextBoolean()
	protected static final boolean COLLECTIBLE_COMPANY = LogicUtil.nextBoolean()
	protected static final boolean CONGLOMERATE = LogicUtil.nextBoolean()
	protected static final boolean DIGITAL_VISUAL_EFFECTS_COMPANY = LogicUtil.nextBoolean()
	protected static final boolean DISTRIBUTOR = LogicUtil.nextBoolean()
	protected static final boolean GAME_COMPANY = LogicUtil.nextBoolean()
	protected static final boolean FILM_EQUIPMENT_COMPANY = LogicUtil.nextBoolean()
	protected static final boolean MAKE_UP_EFFECTS_STUDIO = LogicUtil.nextBoolean()
	protected static final boolean MATTE_PAINTING_COMPANY = LogicUtil.nextBoolean()
	protected static final boolean MODEL_AND_MINIATURE_EFFECTS_COMPANY = LogicUtil.nextBoolean()
	protected static final boolean POST_PRODUCTION_COMPANY = LogicUtil.nextBoolean()
	protected static final boolean PRODUCTION_COMPANY = LogicUtil.nextBoolean()
	protected static final boolean PROP_COMPANY = LogicUtil.nextBoolean()
	protected static final boolean RECORD_LABEL = LogicUtil.nextBoolean()
	protected static final boolean SPECIAL_EFFECTS_COMPANY = LogicUtil.nextBoolean()
	protected static final boolean TV_AND_FILM_PRODUCTION_COMPANY = LogicUtil.nextBoolean()
	protected static final boolean VIDEO_GAME_COMPANY = LogicUtil.nextBoolean()

}
