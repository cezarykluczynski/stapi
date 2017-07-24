package com.cezarykluczynski.stapi.server.country.mapper;

import com.cezarykluczynski.stapi.model.country.entity.Country;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class})
public interface CountrySoapMapper {

	com.cezarykluczynski.stapi.client.v1.soap.Country map(Country country);

}
