package com.cezarykluczynski.stapi.server.location.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.LocationBase;
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationV2Base;
import com.cezarykluczynski.stapi.model.location.dto.LocationRequestDTO;
import com.cezarykluczynski.stapi.model.location.entity.Location;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.location.dto.LocationRestBeanParams;
import com.cezarykluczynski.stapi.server.location.dto.LocationV2RestBeanParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface LocationBaseRestMapper {

	@Mapping(target = "qonosLocation", ignore = true)
	@Mapping(target = "mythologicalLocation", ignore = true)
	@Mapping(target = "restaurant", ignore = true)
	@Mapping(target = "residence", ignore = true)
	LocationRequestDTO mapBase(LocationRestBeanParams locationRestBeanParams);

	@Mapping(target = "tlement", ignore = true) // wrongly generated settlement setter
	@Mapping(target = "landmark", constant = "false")
	LocationBase mapBase(Location location);

	List<LocationBase> mapBase(List<Location> locationList);

	@Mapping(target = "landmark", ignore = true)
	LocationRequestDTO mapV2Base(LocationV2RestBeanParams locationV2RestBeanParams);

	@Mapping(target = "tlement", ignore = true) // wrongly generated settlement setter
	LocationV2Base mapV2Base(Location location);

	List<LocationV2Base> mapV2Base(List<Location> locationList);

}
