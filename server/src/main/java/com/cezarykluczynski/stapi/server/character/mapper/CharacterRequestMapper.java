package com.cezarykluczynski.stapi.server.character.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.CharacterRequest;
import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO;
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestOrderMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class, RequestOrderMapper.class})
public interface CharacterRequestMapper {

	CharacterRequestDTO map(CharacterRequest performerRequest);

	@Mappings({
			@Mapping(target = "order", ignore = true)
	})
	CharacterRequestDTO map(CharacterRestBeanParams characterRestBeanParams);

}
