package com.cezarykluczynski.stapi.server.weapon.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.WeaponBase;
import com.cezarykluczynski.stapi.model.weapon.dto.WeaponRequestDTO;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.weapon.dto.WeaponRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface WeaponBaseRestMapper {

	WeaponRequestDTO mapBase(WeaponRestBeanParams weaponRestBeanParams);

	WeaponBase mapBase(Weapon weapon);

	List<WeaponBase> mapBase(List<Weapon> weaponList);

}
