package com.cezarykluczynski.stapi.server.character.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.CharacterBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullRequest;
import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerHeaderSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterSpeciesSoapMapper.class, EnumMapper.class, EpisodeHeaderSoapMapper.class,
		MovieHeaderSoapMapper.class, PerformerHeaderSoapMapper.class, RequestSortSoapMapper.class})
public interface CharacterSoapMapper {

	@Mappings({
			@Mapping(target = "guid", ignore = true)
	})
	CharacterRequestDTO mapBase(CharacterBaseRequest characterRequest);

	com.cezarykluczynski.stapi.client.v1.soap.CharacterBase mapBase(Character character);

	List<com.cezarykluczynski.stapi.client.v1.soap.CharacterBase> mapBase(List<Character> characterList);

	@Mappings({
			@Mapping(target = "name", ignore = true),
			@Mapping(target = "gender", ignore = true),
			@Mapping(target = "deceased", ignore = true),
			@Mapping(target = "mirror", ignore = true),
			@Mapping(target = "alternateReality", ignore = true),
			@Mapping(target = "sort", ignore = true)
	})
	CharacterRequestDTO mapFull(CharacterFullRequest characterRequest);

	com.cezarykluczynski.stapi.client.v1.soap.CharacterFull mapFull(Character character);

}
