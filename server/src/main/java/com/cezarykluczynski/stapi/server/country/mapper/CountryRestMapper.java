package com.cezarykluczynski.stapi.server.country.mapper;

import com.cezarykluczynski.stapi.model.country.entity.Country;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class})
public interface CountryRestMapper {

	com.cezarykluczynski.stapi.client.rest.model.Country map(Country country);

}
