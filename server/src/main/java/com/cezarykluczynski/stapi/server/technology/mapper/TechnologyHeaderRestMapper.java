package com.cezarykluczynski.stapi.server.technology.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyHeader;
import com.cezarykluczynski.stapi.model.technology.entity.Technology;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TechnologyHeaderRestMapper {

	TechnologyHeader map(Technology technology);

	List<TechnologyHeader> map(List<Technology> technology);

}
