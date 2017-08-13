package com.cezarykluczynski.stapi.server.weapon.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.WeaponBase;
import com.cezarykluczynski.stapi.client.v1.soap.WeaponBaseRequest;
import com.cezarykluczynski.stapi.model.weapon.dto.WeaponRequestDTO;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface WeaponBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	WeaponRequestDTO mapBase(WeaponBaseRequest weaponBaseRequest);

	WeaponBase mapBase(Weapon weapon);

	List<WeaponBase> mapBase(List<Weapon> weaponList);

}
