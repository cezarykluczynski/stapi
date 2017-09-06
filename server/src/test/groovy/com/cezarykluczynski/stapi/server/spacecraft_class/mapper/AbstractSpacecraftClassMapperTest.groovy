package com.cezarykluczynski.stapi.server.spacecraft_class.mapper

import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.util.AbstractSpacecraftClassTest

abstract class AbstractSpacecraftClassMapperTest extends AbstractSpacecraftClassTest {

	protected SpacecraftClass createSpacecraftClass() {
		new SpacecraftClass(
				uid: UID,
				name: NAME,
				numberOfDecks: NUMBER_OF_DECKS,
				warpCapable: WARP_CAPABLE,
				alternateReality: ALTERNATE_REALITY,
				activeFrom: ACTIVE_FROM,
				activeTo: ACTIVE_TO,
				species: new Species(),
				owner: new Organization(),
				operator: new Organization(),
				affiliation: new Organization(),
				spacecraftTypes: createSetOfRandomNumberOfMocks(SpacecraftType),
				spacecrafts: createSetOfRandomNumberOfMocks(Spacecraft))
	}

}
