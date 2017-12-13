package com.cezarykluczynski.stapi.server.organization.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.OrganizationFull;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseRestMapper.class})
public interface OrganizationFullRestMapper {

	OrganizationFull mapFull(Organization organization);

}
