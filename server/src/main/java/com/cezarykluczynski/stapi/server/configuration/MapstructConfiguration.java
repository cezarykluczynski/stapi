package com.cezarykluczynski.stapi.server.configuration;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
		unmappedTargetPolicy = ReportingPolicy.ERROR
)
public class MapstructConfiguration {
}
