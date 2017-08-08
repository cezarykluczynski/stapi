package com.cezarykluczynski.stapi.server.platform.mapper;

import com.cezarykluczynski.stapi.model.platform.entity.Platform;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface PlatformRestMapper {

	com.cezarykluczynski.stapi.client.v1.rest.model.Platform map(Platform platform);

}

