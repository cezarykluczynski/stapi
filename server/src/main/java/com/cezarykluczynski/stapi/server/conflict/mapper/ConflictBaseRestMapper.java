package com.cezarykluczynski.stapi.server.conflict.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictBase;
import com.cezarykluczynski.stapi.model.conflict.dto.ConflictRequestDTO;
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.conflict.dto.ConflictRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface ConflictBaseRestMapper {

	ConflictRequestDTO mapBase(ConflictRestBeanParams conflictRestBeanParams);

	ConflictBase mapBase(Conflict conflict);

	List<ConflictBase> mapBase(List<Conflict> conflictList);

}
