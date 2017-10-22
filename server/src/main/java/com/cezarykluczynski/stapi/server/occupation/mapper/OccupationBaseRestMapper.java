package com.cezarykluczynski.stapi.server.occupation.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.OccupationBase;
import com.cezarykluczynski.stapi.model.occupation.dto.OccupationRequestDTO;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.occupation.dto.OccupationRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface OccupationBaseRestMapper {

	OccupationRequestDTO mapBase(OccupationRestBeanParams occupationRestBeanParams);

	OccupationBase mapBase(Occupation occupation);

	List<OccupationBase> mapBase(List<Occupation> occupationList);

}
