package com.cezarykluczynski.stapi.server.spacecraft_class.mapper;

import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassHeader;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SpacecraftClassHeaderRestMapper {

	SpacecraftClassHeader map(SpacecraftClass spacecraftClass);

	List<SpacecraftClassHeader> map(List<SpacecraftClass> spacecraftClass);

}
