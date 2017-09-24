package com.cezarykluczynski.stapi.server.material.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialFull;
import com.cezarykluczynski.stapi.model.material.entity.Material;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface MaterialFullRestMapper {

	MaterialFull mapFull(Material material);

}
