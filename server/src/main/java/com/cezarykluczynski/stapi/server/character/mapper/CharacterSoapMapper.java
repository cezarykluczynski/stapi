package com.cezarykluczynski.stapi.server.character.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.CharacterRequest;
import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestOrderMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerHeaderSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class, PerformerHeaderSoapMapper.class,
		RequestOrderMapper.class})
public interface CharacterSoapMapper {

	CharacterRequestDTO map(CharacterRequest performerRequest);

	@Mappings({
			@Mapping(source = "performers", target = "performerHeaders")
	})
	com.cezarykluczynski.stapi.client.v1.soap.Character map(Character character);

	List<com.cezarykluczynski.stapi.client.v1.soap.Character> map(List<Character> characterList);

}
