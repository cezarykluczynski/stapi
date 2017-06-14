package com.cezarykluczynski.stapi.server.character.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.CharacterHeader;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CharacterHeaderSoapMapper {

	CharacterHeader map(Character character);

	List<CharacterHeader> map(List<Character> character);

}
