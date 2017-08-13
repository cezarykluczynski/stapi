package com.cezarykluczynski.stapi.server.weapon.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.WeaponHeader;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WeaponHeaderSoapMapper {

	WeaponHeader map(Weapon weapon);

	List<WeaponHeader> map(List<Weapon> weapon);

}
