package com.cezarykluczynski.stapi.server.location.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.LocationFull;
import com.cezarykluczynski.stapi.model.location.entity.Location;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class)
public interface LocationFullRestMapper {

	@Mapping(target = "tlement", ignore = true)
	LocationFull mapFull(Location location);

}
