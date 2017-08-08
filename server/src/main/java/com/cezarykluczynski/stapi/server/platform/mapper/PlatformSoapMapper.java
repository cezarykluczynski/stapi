package com.cezarykluczynski.stapi.server.platform.mapper;

import com.cezarykluczynski.stapi.model.platform.entity.Platform;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface PlatformSoapMapper {

	com.cezarykluczynski.stapi.client.v1.soap.Platform map(Platform platform);

}
