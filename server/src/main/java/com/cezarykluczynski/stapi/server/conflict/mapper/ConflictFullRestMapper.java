package com.cezarykluczynski.stapi.server.conflict.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.ConflictFull;
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.location.mapper.LocationBaseRestMapper;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseRestMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseRestMapper.class, LocationBaseRestMapper.class, OrganizationBaseRestMapper.class})
public interface ConflictFullRestMapper {

	ConflictFull mapFull(Conflict conflict);

}
