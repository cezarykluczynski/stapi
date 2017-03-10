package com.cezarykluczynski.stapi.server.species.mapper;


import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesHeader;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SpeciesHeaderRestMapper {

	SpeciesHeader map(Species performer);

	List<SpeciesHeader> map(List<Species> performer);

}
