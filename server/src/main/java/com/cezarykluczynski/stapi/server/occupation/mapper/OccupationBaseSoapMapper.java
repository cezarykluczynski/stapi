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
	OccupationRequestDTO mapBase(OccupationBaseRequest occupationBaseRequest);

	OccupationBase mapBase(Occupation occupation);

	List<OccupationBase> mapBase(List<Occupation> occupationList);

}
