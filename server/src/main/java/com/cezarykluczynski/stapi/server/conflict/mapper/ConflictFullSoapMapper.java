package com.cezarykluczynski.stapi.server.conflict.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.ConflictFull;
import com.cezarykluczynski.stapi.client.v1.soap.ConflictFullRequest;
import com.cezarykluczynski.stapi.model.conflict.dto.ConflictRequestDTO;
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.location.mapper.LocationBaseSoapMapper;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseSoapMapper.class, LocationBaseSoapMapper.class, OrganizationBaseSoapMapper.class})
public interface ConflictFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "yearFrom", ignore = true)
	@Mapping(target = "yearTo", ignore = true)
	@Mapping(target = "earthConflict", ignore = true)
	@Mapping(target = "federationWar", ignore = true)
	@Mapping(target = "klingonWar", ignore = true)
	@Mapping(target = "dominionWarBattle", ignore = true)
	@Mapping(target = "alternateReality", ignore = true)
	@Mapping(target = "sort", ignore = true)
	ConflictRequestDTO mapFull(ConflictFullRequest conflictFullRequest);

	ConflictFull mapFull(Conflict conflict);

}
