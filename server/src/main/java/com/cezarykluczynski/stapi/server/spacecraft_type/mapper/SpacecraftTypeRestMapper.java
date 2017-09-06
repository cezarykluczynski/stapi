package com.cezarykluczynski.stapi.server.spacecraft_type.mapper;

import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class})
public interface SpacecraftTypeRestMapper {

	com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftType map(SpacecraftType spacecraftType);

}
