package com.cezarykluczynski.stapi.server.spacecraft.mapper;

import com.cezarykluczynski.stapi.client.rest.model.SpacecraftBase;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftV2Base;
import com.cezarykluczynski.stapi.model.spacecraft.dto.SpacecraftRequestDTO;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.spacecraft.dto.SpacecraftRestBeanParams;
import com.cezarykluczynski.stapi.server.spacecraft.dto.SpacecraftV2RestBeanParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface SpacecraftBaseRestMapper {

	@Mapping(target = "registry", ignore = true)
	@Mapping(target = "status", ignore = true)
	SpacecraftRequestDTO mapBase(SpacecraftRestBeanParams spacecraftRestBeanParams);

	SpacecraftBase mapBase(Spacecraft spacecraft);

	List<SpacecraftBase> mapBase(List<Spacecraft> spacecraftList);

	SpacecraftRequestDTO mapV2Base(SpacecraftV2RestBeanParams spacecraftV2RestBeanParams);

	SpacecraftV2Base mapV2Base(Spacecraft spacecraft);

	List<SpacecraftV2Base> mapV2Base(List<Spacecraft> spacecraftList);

}
