package com.cezarykluczynski.stapi.server.technology.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyBase;
import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyV2Base;
import com.cezarykluczynski.stapi.model.technology.dto.TechnologyRequestDTO;
import com.cezarykluczynski.stapi.model.technology.entity.Technology;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.technology.dto.TechnologyRestBeanParams;
import com.cezarykluczynski.stapi.server.technology.dto.TechnologyV2RestBeanParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface TechnologyBaseRestMapper {

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
	TechnologyRequestDTO mapBase(TechnologyRestBeanParams technologyRestBeanParams);

	TechnologyBase mapBase(Technology technology);

	List<TechnologyBase> mapBase(List<Technology> technologyList);

	TechnologyRequestDTO mapV2Base(TechnologyV2RestBeanParams technologyV2RestBeanParams);

	TechnologyV2Base mapV2Base(Technology technology);

	List<TechnologyV2Base> mapV2Base(List<Technology> technologyList);

}
