package com.cezarykluczynski.stapi.server.species.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesBase;
import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectHeaderRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.species.dto.SpeciesRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {AstronomicalObjectHeaderRestMapper.class, RequestSortRestMapper.class})
public interface SpeciesBaseRestMapper {

	SpeciesRequestDTO mapBase(SpeciesRestBeanParams speciesRestBeanParams);

	SpeciesBase mapBase(Species series);

	List<SpeciesBase> mapBase(List<Species> series);

}
