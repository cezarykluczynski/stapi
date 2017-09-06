package com.cezarykluczynski.stapi.server.spacecraft.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBase;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBaseRequest;
import com.cezarykluczynski.stapi.model.spacecraft.dto.SpacecraftRequestDTO;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesHeaderSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {OrganizationHeaderSoapMapper.class, RequestSortSoapMapper.class,
		SpeciesHeaderSoapMapper.class})
public interface SpacecraftBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	SpacecraftRequestDTO mapBase(SpacecraftBaseRequest spacecraftBaseRequest);

	SpacecraftBase mapBase(Spacecraft spacecraft);

	List<SpacecraftBase> mapBase(List<Spacecraft> spacecraftList);

}
