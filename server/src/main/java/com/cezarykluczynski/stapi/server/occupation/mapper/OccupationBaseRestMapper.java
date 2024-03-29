package com.cezarykluczynski.stapi.server.occupation.mapper;

import com.cezarykluczynski.stapi.client.rest.model.OccupationBase;
import com.cezarykluczynski.stapi.client.rest.model.OccupationV2Base;
import com.cezarykluczynski.stapi.model.occupation.dto.OccupationRequestDTO;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.occupation.dto.OccupationRestBeanParams;
import com.cezarykluczynski.stapi.server.occupation.dto.OccupationV2RestBeanParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface OccupationBaseRestMapper {

	@Mapping(target = "artsOccupation", ignore = true)
	@Mapping(target = "communicationOccupation", ignore = true)
	@Mapping(target = "economicOccupation", ignore = true)
	@Mapping(target = "educationOccupation", ignore = true)
	@Mapping(target = "entertainmentOccupation", ignore = true)
	@Mapping(target = "illegalOccupation", ignore = true)
	@Mapping(target = "sportsOccupation", ignore = true)
	@Mapping(target = "victualOccupation", ignore = true)
	OccupationRequestDTO mapBase(OccupationRestBeanParams occupationRestBeanParams);

	OccupationBase mapBase(Occupation occupation);

	List<OccupationBase> mapBase(List<Occupation> occupationList);

	OccupationRequestDTO mapV2Base(OccupationV2RestBeanParams occupationRestBeanParams);

	OccupationV2Base mapV2Base(Occupation occupation);

	List<OccupationV2Base> mapV2Base(List<Occupation> occupationList);

}
