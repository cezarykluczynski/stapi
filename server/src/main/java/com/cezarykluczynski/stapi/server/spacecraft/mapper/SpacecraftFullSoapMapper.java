package com.cezarykluczynski.stapi.server.spacecraft.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFull;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFullRequest;
import com.cezarykluczynski.stapi.model.spacecraft.dto.SpacecraftRequestDTO;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseSoapMapper;
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassBaseSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {SpacecraftClassBaseSoapMapper.class, OrganizationBaseSoapMapper.class})
public interface SpacecraftFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "sort", ignore = true)
	SpacecraftRequestDTO mapFull(SpacecraftFullRequest spacecraftFullRequest);

	SpacecraftFull mapFull(Spacecraft spacecraft);

}
