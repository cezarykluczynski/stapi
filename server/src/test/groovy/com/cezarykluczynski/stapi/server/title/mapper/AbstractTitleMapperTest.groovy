package com.cezarykluczynski.stapi.server.title.mapper

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.title.entity.Title
import com.cezarykluczynski.stapi.util.AbstractTitleTest

abstract class AbstractTitleMapperTest extends AbstractTitleTest {

	protected Title createTitle() {
		new Title(
				uid: UID,
				name: NAME,
				militaryRank: MILITARY_RANK,
				fleetRank: FLEET_RANK,
				religiousTitle: RELIGIOUS_TITLE,
				position: POSITION,
				mirror: MIRROR,
				characters: createSetOfRandomNumberOfMocks(Character))
	}

}
