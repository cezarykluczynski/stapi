package com.cezarykluczynski.stapi.server.location.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.LocationFull;
import com.cezarykluczynski.stapi.client.v1.soap.LocationFullRequest;
import com.cezarykluczynski.stapi.model.location.dto.LocationRequestDTO;
import com.cezarykluczynski.stapi.model.location.entity.Location;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface LocationFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "earthlyLocation", ignore = true)
	@Mapping(target = "fictionalLocation", ignore = true)
	@Mapping(target = "religiousLocation", ignore = true)
	@Mapping(target = "geographicalLocation", ignore = true)
	@Mapping(target = "bodyOfWater", ignore = true)
	@Mapping(target = "country", ignore = true)
	@Mapping(target = "subnationalEntity", ignore = true)
	@Mapping(target = "settlement", ignore = true)
	@Mapping(target = "usSettlement", ignore = true)
	@Mapping(target = "bajoranSettlement", ignore = true)
	@Mapping(target = "colony", ignore = true)
	@Mapping(target = "landform", ignore = true)
	@Mapping(target = "landmark", ignore = true)
	@Mapping(target = "road", ignore = true)
	@Mapping(target = "structure", ignore = true)
	@Mapping(target = "shipyard", ignore = true)
	@Mapping(target = "buildingInterior", ignore = true)
	@Mapping(target = "establishment", ignore = true)
	@Mapping(target = "medicalEstablishment", ignore = true)
	@Mapping(target = "ds9Establishment", ignore = true)
	@Mapping(target = "school", ignore = true)
	@Mapping(target = "mirror", ignore = true)
	@Mapping(target = "alternateReality", ignore = true)
	@Mapping(target = "sort", ignore = true)
	LocationRequestDTO mapFull(LocationFullRequest locationFullRequest);

	LocationFull mapFull(Location location);

}
