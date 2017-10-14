package com.cezarykluczynski.stapi.server.animal.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.AnimalHeader;
import com.cezarykluczynski.stapi.model.animal.entity.Animal;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnimalHeaderSoapMapper {

	AnimalHeader map(Animal animal);

	List<AnimalHeader> map(List<Animal> animal);

}
