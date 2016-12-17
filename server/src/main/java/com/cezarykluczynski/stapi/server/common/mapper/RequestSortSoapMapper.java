package com.cezarykluczynski.stapi.server.common.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.RequestSort;
import com.cezarykluczynski.stapi.client.v1.soap.RequestSortClause;
import com.cezarykluczynski.stapi.model.common.dto.RequestSortClauseDTO;
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortDirectionEnumDTOMapper.class})
public interface RequestSortSoapMapper {

	RequestSortDTO mapRequestSort(RequestSort requestSort);

	RequestSortClauseDTO mapRequestSortClause(RequestSortClause requestSortClause);

}
