package com.cezarykluczynski.stapi.server.species.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFull;
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFullRequest;
import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectBaseSoapMapper;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {AstronomicalObjectBaseSoapMapper.class, CharacterBaseSoapMapper.class})
public interface SpeciesFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "extinctSpecies", ignore = true)
	@Mapping(target = "warpCapableSpecies", ignore = true)
	@Mapping(target = "extraGalacticSpecies", ignore = true)
	@Mapping(target = "humanoidSpecies", ignore = true)
	@Mapping(target = "reptilianSpecies", ignore = true)
	@Mapping(target = "nonCorporealSpecies", ignore = true)
	@Mapping(target = "shapeshiftingSpecies", ignore = true)
	@Mapping(target = "spaceborneSpecies", ignore = true)
	@Mapping(target = "telepathicSpecies", ignore = true)
	@Mapping(target = "transDimensionalSpecies", ignore = true)
	@Mapping(target = "unnamedSpecies", ignore = true)
	@Mapping(target = "alternateReality", ignore = true)
	@Mapping(target = "sort", ignore = true)
	SpeciesRequestDTO mapFull(SpeciesFullRequest speciesFullRequest);

	SpeciesFull mapFull(Species species);

}
