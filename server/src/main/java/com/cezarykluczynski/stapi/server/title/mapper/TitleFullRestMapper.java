package com.cezarykluczynski.stapi.server.title.mapper;

import com.cezarykluczynski.stapi.client.rest.model.TitleFull;
import com.cezarykluczynski.stapi.client.rest.model.TitleV2Full;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseRestMapper.class})
public interface TitleFullRestMapper {

	@Mapping(target = "position", constant = "false")
	TitleFull mapFull(Title title);

	TitleV2Full mapV2Full(Title title);

}
