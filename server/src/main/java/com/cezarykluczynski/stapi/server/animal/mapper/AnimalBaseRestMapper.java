package com.cezarykluczynski.stapi.server.animal.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalBase;
import com.cezarykluczynski.stapi.model.animal.dto.AnimalRequestDTO;
import com.cezarykluczynski.stapi.model.animal.entity.Animal;
import com.cezarykluczynski.stapi.server.animal.dto.AnimalRestBeanParams;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface AnimalBaseRestMapper {

	AnimalRequestDTO mapBase(AnimalRestBeanParams animalRestBeanParams);

	AnimalBase mapBase(Animal animal);

	List<AnimalBase> mapBase(List<Animal> animalList);

}
