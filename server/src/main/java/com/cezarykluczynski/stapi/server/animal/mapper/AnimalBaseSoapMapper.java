package com.cezarykluczynski.stapi.server.animal.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.AnimalBase;
import com.cezarykluczynski.stapi.client.v1.soap.AnimalBaseRequest;
import com.cezarykluczynski.stapi.model.animal.dto.AnimalRequestDTO;
import com.cezarykluczynski.stapi.model.animal.entity.Animal;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface AnimalBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	AnimalRequestDTO mapBase(AnimalBaseRequest animalBaseRequest);

	AnimalBase mapBase(Animal animal);

	List<AnimalBase> mapBase(List<Animal> animalList);

}
