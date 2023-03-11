package com.cezarykluczynski.stapi.server.location.mapper;

import com.cezarykluczynski.stapi.client.rest.model.LocationFull;
import com.cezarykluczynski.stapi.client.rest.model.LocationV2Full;
import com.cezarykluczynski.stapi.model.location.entity.Location;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class)
public interface LocationFullRestMapper {

	@Mapping(target = "landmark", constant = "false")
	LocationFull mapFull(Location location);

	LocationV2Full mapV2Full(Location location);

}
