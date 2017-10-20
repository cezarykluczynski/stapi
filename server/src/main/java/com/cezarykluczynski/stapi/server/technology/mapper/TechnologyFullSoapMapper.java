package com.cezarykluczynski.stapi.server.technology.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFull;
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFullRequest;
import com.cezarykluczynski.stapi.model.technology.dto.TechnologyRequestDTO;
import com.cezarykluczynski.stapi.model.technology.entity.Technology;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface TechnologyFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "borgTechnology", ignore = true)
	@Mapping(target = "borgComponent", ignore = true)
	@Mapping(target = "communicationsTechnology", ignore = true)
	@Mapping(target = "computerTechnology", ignore = true)
	@Mapping(target = "computerProgramming", ignore = true)
	@Mapping(target = "subroutine", ignore = true)
	@Mapping(target = "database", ignore = true)
	@Mapping(target = "energyTechnology", ignore = true)
	@Mapping(target = "fictionalTechnology", ignore = true)
	@Mapping(target = "holographicTechnology", ignore = true)
	@Mapping(target = "identificationTechnology", ignore = true)
	@Mapping(target = "lifeSupportTechnology", ignore = true)
	@Mapping(target = "sensorTechnology", ignore = true)
	@Mapping(target = "shieldTechnology", ignore = true)
	@Mapping(target = "tool", ignore = true)
	@Mapping(target = "culinaryTool", ignore = true)
	@Mapping(target = "engineeringTool", ignore = true)
	@Mapping(target = "householdTool", ignore = true)
	@Mapping(target = "medicalEquipment", ignore = true)
	@Mapping(target = "transporterTechnology", ignore = true)
	@Mapping(target = "sort", ignore = true)
	TechnologyRequestDTO mapFull(TechnologyFullRequest technologyFullRequest);

	TechnologyFull mapFull(Technology technology);

}
