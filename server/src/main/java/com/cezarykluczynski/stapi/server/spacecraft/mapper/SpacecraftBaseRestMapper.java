package com.cezarykluczynski.stapi.server.spacecraft.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftBase;
import com.cezarykluczynski.stapi.model.spacecraft.dto.SpacecraftRequestDTO;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.spacecraft.dto.SpacecraftRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface SpacecraftBaseRestMapper {

	SpacecraftRequestDTO mapBase(SpacecraftRestBeanParams spacecraftRestBeanParams);

	SpacecraftBase mapBase(Spacecraft spacecraft);

	List<SpacecraftBase> mapBase(List<Spacecraft> spacecraftList);

}
