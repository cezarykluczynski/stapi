package com.cezarykluczynski.stapi.server.common.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.RequestSortDirectionEnum;
import com.cezarykluczynski.stapi.model.common.dto.enums.RequestSortDirectionDTO;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface RequestSortDirectionEnumDTOMapper {

	RequestSortDirectionDTO map(RequestSortDirectionEnum requestSortDirectionEnum);

}
