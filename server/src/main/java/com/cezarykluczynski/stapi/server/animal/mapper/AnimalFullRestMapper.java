package com.cezarykluczynski.stapi.server.animal.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalFull;
import com.cezarykluczynski.stapi.model.animal.entity.Animal;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface AnimalFullRestMapper {

	AnimalFull mapFull(Animal animal);

}
