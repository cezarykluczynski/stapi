package com.cezarykluczynski.stapi.server.character.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.CharacterBase;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseRequest;
import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseSoapMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterSpeciesSoapMapper.class, EnumMapper.class, EpisodeBaseSoapMapper.class,
		MovieHeaderSoapMapper.class, PerformerBaseSoapMapper.class, RequestSortSoapMapper.class})
public interface CharacterBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	CharacterRequestDTO mapBase(CharacterBaseRequest characterRequest);

	CharacterBase mapBase(Character character);

	List<CharacterBase> mapBase(List<Character> characterList);

}
