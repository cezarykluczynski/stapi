package com.cezarykluczynski.stapi.server.spacecraft.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftHeader;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SpacecraftHeaderSoapMapper {

	SpacecraftHeader map(Spacecraft spacecraft);

	List<SpacecraftHeader> map(List<Spacecraft> spacecraft);

}
