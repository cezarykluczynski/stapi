package com.cezarykluczynski.stapi.server.species.mapper;

import com.cezarykluczynski.stapi.client.rest.model.SpeciesBase;
import com.cezarykluczynski.stapi.client.rest.model.SpeciesV2Base;
import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectHeaderRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.species.dto.SpeciesRestBeanParams;
import com.cezarykluczynski.stapi.server.species.dto.SpeciesV2RestBeanParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {AstronomicalObjectHeaderRestMapper.class, RequestSortRestMapper.class})
public interface SpeciesBaseRestMapper {

	@Mapping(target = "avianSpecies", ignore = true)
	SpeciesRequestDTO mapBase(SpeciesRestBeanParams speciesRestBeanParams);

	SpeciesBase mapBase(Species series);

	List<SpeciesBase> mapBase(List<Species> series);

	SpeciesRequestDTO mapV2Base(SpeciesV2RestBeanParams speciesV2RestBeanParams);

	SpeciesV2Base mapV2Base(Species series);

	List<SpeciesV2Base> mapV2Base(List<Species> series);


}
