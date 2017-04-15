package com.cezarykluczynski.stapi.server.location.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.LocationBase;
import com.cezarykluczynski.stapi.model.location.dto.LocationRequestDTO;
import com.cezarykluczynski.stapi.model.location.entity.Location;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.location.dto.LocationRestBeanParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface LocationBaseRestMapper {

	LocationRequestDTO mapBase(LocationRestBeanParams locationRestBeanParams);

	@Mapping(target = "tlement", ignore = true)
	LocationBase mapBase(Location location);

	List<LocationBase> mapBase(List<Location> locationList);

}
