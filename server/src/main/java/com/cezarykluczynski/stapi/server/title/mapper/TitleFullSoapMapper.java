package com.cezarykluczynski.stapi.server.title.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.TitleFull;
import com.cezarykluczynski.stapi.client.v1.soap.TitleFullRequest;
import com.cezarykluczynski.stapi.model.title.dto.TitleRequestDTO;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseSoapMapper.class, RequestSortSoapMapper.class})
public interface TitleFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "militaryRank", ignore = true)
	@Mapping(target = "fleetRank", ignore = true)
	@Mapping(target = "religiousTitle", ignore = true)
	@Mapping(target = "position", ignore = true)
	@Mapping(target = "mirror", ignore = true)
	@Mapping(target = "sort", ignore = true)
	TitleRequestDTO mapFull(TitleFullRequest titleFullRequest);

	TitleFull mapFull(Title title);

}
