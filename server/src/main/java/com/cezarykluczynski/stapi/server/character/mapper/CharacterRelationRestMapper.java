package com.cezarykluczynski.stapi.server.character.mapper;

import com.cezarykluczynski.stapi.model.character.entity.CharacterRelation;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CharacterHeaderRestMapper.class})
public interface CharacterRelationRestMapper {

	com.cezarykluczynski.stapi.client.rest.model.CharacterRelation map(CharacterRelation characterRelation);

}
