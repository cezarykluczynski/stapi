package com.cezarykluczynski.stapi.server.technology.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.TechnologyBase;
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyBaseRequest;
import com.cezarykluczynski.stapi.model.technology.dto.TechnologyRequestDTO;
import com.cezarykluczynski.stapi.model.technology.entity.Technology;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface TechnologyBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	@Mapping(target = "securityTechnology", ignore = true)
	@Mapping(target = "propulsionTechnology", ignore = true)
	@Mapping(target = "spacecraftComponent", ignore = true)
	@Mapping(target = "warpTechnology", ignore = true)
	@Mapping(target = "transwarpTechnology", ignore = true)
	@Mapping(target = "timeTravelTechnology", ignore = true)
	@Mapping(target = "militaryTechnology", ignore = true)
	@Mapping(target = "victualTechnology", ignore = true)
	@Mapping(target = "transportationTechnology", ignore = true)
	@Mapping(target = "weaponComponent", ignore = true)
	@Mapping(target = "artificialLifeformComponent", ignore = true)
	TechnologyRequestDTO mapBase(TechnologyBaseRequest technologyBaseRequest);

	TechnologyBase mapBase(Technology technology);

	List<TechnologyBase> mapBase(List<Technology> technologyList);

}
