package com.cezarykluczynski.stapi.server.literature.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.LiteratureFull;
import com.cezarykluczynski.stapi.model.literature.entity.Literature;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface LiteratureFullRestMapper {

	LiteratureFull mapFull(Literature literature);

}
