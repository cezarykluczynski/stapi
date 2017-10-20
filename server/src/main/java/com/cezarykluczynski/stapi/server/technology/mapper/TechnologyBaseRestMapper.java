package com.cezarykluczynski.stapi.server.technology.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyBase;
import com.cezarykluczynski.stapi.model.technology.dto.TechnologyRequestDTO;
import com.cezarykluczynski.stapi.model.technology.entity.Technology;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.technology.dto.TechnologyRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface TechnologyBaseRestMapper {

	TechnologyRequestDTO mapBase(TechnologyRestBeanParams technologyRestBeanParams);

	TechnologyBase mapBase(Technology technology);

	List<TechnologyBase> mapBase(List<Technology> technologyList);

}
