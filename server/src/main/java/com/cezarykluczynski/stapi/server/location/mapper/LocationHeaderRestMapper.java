package com.cezarykluczynski.stapi.server.location.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.LocationHeader;
import com.cezarykluczynski.stapi.model.location.entity.Location;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocationHeaderRestMapper {

	LocationHeader map(Location location);

	List<LocationHeader> map(List<Location> location);

}
