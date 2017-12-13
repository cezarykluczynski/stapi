package com.cezarykluczynski.stapi.server.title.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.TitleFull;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseRestMapper.class})
public interface TitleFullRestMapper {

	TitleFull mapFull(Title title);

}
