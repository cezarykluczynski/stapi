package com.cezarykluczynski.stapi.server.common.mapper;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import com.cezarykluczynski.stapi.server.common.serializer.RestSortDeserializer;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortDirectionEnumDTOMapper.class})
public interface RequestSortRestMapper {

	default RequestSortDTO mapString(String sort) {
		RequestSortDTO requestSortDTO = new RequestSortDTO();
		requestSortDTO.setClauses(RestSortDeserializer.deserialize(sort));
		return requestSortDTO;
	}

}
