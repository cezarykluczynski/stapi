package com.cezarykluczynski.stapi.server.weapon.mapper;

import com.cezarykluczynski.stapi.client.rest.model.WeaponFull;
import com.cezarykluczynski.stapi.client.rest.model.WeaponV2Full;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface WeaponFullRestMapper {

	WeaponFull mapFull(Weapon weapon);

	WeaponV2Full mapV2Full(Weapon weapon);

}
