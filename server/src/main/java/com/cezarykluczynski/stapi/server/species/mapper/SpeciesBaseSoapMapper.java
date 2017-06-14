package com.cezarykluczynski.stapi.server.species.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBase;
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBaseRequest;
import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {AstronomicalObjectHeaderSoapMapper.class, RequestSortSoapMapper.class})
public interface SpeciesBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	SpeciesRequestDTO mapBase(SpeciesBaseRequest speciesBaseRequest);

	SpeciesBase mapBase(Species species);

	List<SpeciesBase> mapBase(List<Species> speciesList);

}
