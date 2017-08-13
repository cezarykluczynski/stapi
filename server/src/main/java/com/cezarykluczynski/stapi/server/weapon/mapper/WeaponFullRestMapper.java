package com.cezarykluczynski.stapi.server.weapon.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponFull;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface WeaponFullRestMapper {

	WeaponFull mapFull(Weapon weapon);

}
