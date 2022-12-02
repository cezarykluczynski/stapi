package com.cezarykluczynski.stapi.server.weapon.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponBase;
import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponV2Base;
import com.cezarykluczynski.stapi.model.weapon.dto.WeaponRequestDTO;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.weapon.dto.WeaponRestBeanParams;
import com.cezarykluczynski.stapi.server.weapon.dto.WeaponV2RestBeanParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface WeaponBaseRestMapper {

	@Mapping(target = "directedEnergyWeapon", ignore = true)
	@Mapping(target = "explosiveWeapon", ignore = true)
	@Mapping(target = "projectileWeapon", ignore = true)
	@Mapping(target = "fictionalWeapon", ignore = true)
	WeaponRequestDTO mapBase(WeaponRestBeanParams weaponRestBeanParams);

	WeaponBase mapBase(Weapon weapon);

	List<WeaponBase> mapBase(List<Weapon> weaponList);

	WeaponRequestDTO mapV2Base(WeaponV2RestBeanParams weaponV2RestBeanParams);

	WeaponV2Base mapV2Base(Weapon weapon);

	List<WeaponV2Base> mapV2Base(List<Weapon> weaponList);

}
