package com.cezarykluczynski.stapi.server.spacecraft_class.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassBase;
import com.cezarykluczynski.stapi.model.spacecraft_class.dto.SpacecraftClassRequestDTO;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationHeaderRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft_class.dto.SpacecraftClassRestBeanParams;
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesHeaderRestMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {OrganizationHeaderRestMapper.class, RequestSortRestMapper.class,
		SpeciesHeaderRestMapper.class})
public interface SpacecraftClassBaseRestMapper {

	SpacecraftClassRequestDTO mapBase(SpacecraftClassRestBeanParams spacecraftClassRestBeanParams);

	SpacecraftClassBase mapBase(SpacecraftClass spacecraftClass);

	List<SpacecraftClassBase> mapBase(List<SpacecraftClass> spacecraftClassList);

}
