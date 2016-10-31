package com.cezarykluczynski.stapi.server.performer.mapper;

import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class})
public interface PerformerRestMapper {

	com.cezarykluczynski.stapi.client.rest.model.Performer map(Performer series);

	List<com.cezarykluczynski.stapi.client.rest.model.Performer> map(List<Performer> series);

}
