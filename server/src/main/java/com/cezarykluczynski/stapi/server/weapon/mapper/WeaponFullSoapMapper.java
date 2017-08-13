package com.cezarykluczynski.stapi.server.weapon.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.WeaponFull;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponFullRequest;
import com.cezarykluczynski.stapi.model.weapon.dto.WeaponRequestDTO;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface WeaponFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "handHeldWeapon", ignore = true)
	@Mapping(target = "laserTechnology", ignore = true)
	@Mapping(target = "plasmaTechnology", ignore = true)
	@Mapping(target = "photonicTechnology", ignore = true)
	@Mapping(target = "phaserTechnology", ignore = true)
	@Mapping(target = "mirror", ignore = true)
	@Mapping(target = "alternateReality", ignore = true)
	@Mapping(target = "sort", ignore = true)
	WeaponRequestDTO mapFull(WeaponFullRequest weaponFullRequest);

	WeaponFull mapFull(Weapon weapon);

}
