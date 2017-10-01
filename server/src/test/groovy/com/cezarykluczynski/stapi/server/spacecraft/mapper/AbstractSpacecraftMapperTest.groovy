package com.cezarykluczynski.stapi.server.spacecraft.mapper

import com.cezarykluczynski.stapi.model.organization.entity.Organization
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType
import com.cezarykluczynski.stapi.util.AbstractSpacecraftTest

abstract class AbstractSpacecraftMapperTest extends AbstractSpacecraftTest {

	protected Spacecraft createSpacecraft() {
		new Spacecraft(
				uid: UID,
				name: NAME,
				registry: REGISTRY,
				status: STATUS,
				dateStatus: DATE_STATUS,
				spacecraftClass: new SpacecraftClass(),
				owner: new Organization(),
				operator: new Organization(),
				spacecraftTypes: createSetOfRandomNumberOfMocks(SpacecraftType))
	}

}
