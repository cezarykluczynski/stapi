package com.cezarykluczynski.stapi.server.character.mapper;


import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterHeader;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CharacterHeaderRestMapper {

	CharacterHeader map(Character performer);

	List<CharacterHeader> map(List<Character> performer);

}
