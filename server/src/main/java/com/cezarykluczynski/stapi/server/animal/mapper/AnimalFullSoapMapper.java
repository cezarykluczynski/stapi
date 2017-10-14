package com.cezarykluczynski.stapi.server.animal.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.AnimalFull;
import com.cezarykluczynski.stapi.client.v1.soap.AnimalFullRequest;
import com.cezarykluczynski.stapi.model.animal.dto.AnimalRequestDTO;
import com.cezarykluczynski.stapi.model.animal.entity.Animal;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface AnimalFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "earthAnimal", ignore = true)
	@Mapping(target = "earthInsect", ignore = true)
	@Mapping(target = "avian", ignore = true)
	@Mapping(target = "canine", ignore = true)
	@Mapping(target = "feline", ignore = true)
	@Mapping(target = "sort", ignore = true)
	AnimalRequestDTO mapFull(AnimalFullRequest animalFullRequest);

	AnimalFull mapFull(Animal animal);

}
