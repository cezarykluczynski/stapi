package com.cezarykluczynski.stapi.server.reference.mapper;

import com.cezarykluczynski.stapi.model.reference.entity.Reference;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class})
public interface ReferenceSoapMapper {

	com.cezarykluczynski.stapi.client.v1.soap.Reference map(Reference reference);

}
