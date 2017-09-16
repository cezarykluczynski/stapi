package com.cezarykluczynski.stapi.server.character.mapper;

import com.cezarykluczynski.stapi.model.character.entity.CharacterRelation;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {CharacterHeaderSoapMapper.class})
public interface CharacterRelationSoapMapper {

	com.cezarykluczynski.stapi.client.v1.soap.CharacterRelation map(CharacterRelation characterRelation);

}
