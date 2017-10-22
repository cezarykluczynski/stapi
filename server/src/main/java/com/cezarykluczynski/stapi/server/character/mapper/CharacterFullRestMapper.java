package com.cezarykluczynski.stapi.server.character.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterFull;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseRestMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseRestMapper;
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationBaseRestMapper;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseRestMapper;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseRestMapper;
import com.cezarykluczynski.stapi.server.title.mapper.TitleBaseRestMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterRelationRestMapper.class, CharacterSpeciesRestMapper.class, EnumMapper.class,
		EpisodeBaseRestMapper.class, MovieBaseRestMapper.class, OccupationBaseRestMapper.class, OrganizationBaseRestMapper.class,
		PerformerBaseRestMapper.class, TitleBaseRestMapper.class})
public interface CharacterFullRestMapper {

	CharacterFull mapFull(Character series);

}
