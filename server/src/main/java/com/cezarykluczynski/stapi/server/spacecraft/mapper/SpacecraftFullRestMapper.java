package com.cezarykluczynski.stapi.server.spacecraft.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftFull;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassBaseRestMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {SpacecraftClassBaseRestMapper.class, OrganizationBaseRestMapper.class})
public interface SpacecraftFullRestMapper {

	SpacecraftFull mapFull(Spacecraft spacecraft);

}
