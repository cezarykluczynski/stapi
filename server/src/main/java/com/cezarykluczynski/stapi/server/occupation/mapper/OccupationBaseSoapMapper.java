package com.cezarykluczynski.stapi.server.occupation.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.OccupationBase;
import com.cezarykluczynski.stapi.client.v1.soap.OccupationBaseRequest;
import com.cezarykluczynski.stapi.model.occupation.dto.OccupationRequestDTO;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface OccupationBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	@Mapping(target = "artsOccupation", ignore = true)
	@Mapping(target = "communicationOccupation", ignore = true)
	@Mapping(target = "economicOccupation", ignore = true)
	@Mapping(target = "educationOccupation", ignore = true)
	@Mapping(target = "entertainmentOccupation", ignore = true)
	@Mapping(target = "illegalOccupation", ignore = true)
	@Mapping(target = "sportsOccupation", ignore = true)
	@Mapping(target = "victualOccupation", ignore = true)
	OccupationRequestDTO mapBase(OccupationBaseRequest occupationBaseRequest);

	OccupationBase mapBase(Occupation occupation);

	List<OccupationBase> mapBase(List<Occupation> occupationList);

}
