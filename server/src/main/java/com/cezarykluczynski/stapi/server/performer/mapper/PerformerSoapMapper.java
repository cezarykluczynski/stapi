package com.cezarykluczynski.stapi.server.performer.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullRequest;
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieHeaderSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterHeaderSoapMapper.class, DateMapper.class,
		EnumMapper.class, EpisodeHeaderSoapMapper.class, MovieHeaderSoapMapper.class, RequestSortSoapMapper.class})
public interface PerformerSoapMapper {

	@Mappings({
			@Mapping(source = "dateOfBirth.from", target = "dateOfBirthFrom"),
			@Mapping(source = "dateOfBirth.to", target = "dateOfBirthTo"),
			@Mapping(source = "dateOfDeath.from", target = "dateOfDeathFrom"),
			@Mapping(source = "dateOfDeath.to", target = "dateOfDeathTo")
	})
	PerformerRequestDTO mapBase(PerformerBaseRequest performerBaseRequest);

	com.cezarykluczynski.stapi.client.v1.soap.PerformerBase mapBase(Performer performer);

	List<com.cezarykluczynski.stapi.client.v1.soap.PerformerBase> mapBase(List<Performer> performerList);

	@Mappings({
			@Mapping(target = "name", ignore = true),
			@Mapping(target = "birthName", ignore = true),
			@Mapping(target = "gender", ignore = true),
			@Mapping(target = "dateOfBirthFrom", ignore = true),
			@Mapping(target = "dateOfBirthTo", ignore = true),
			@Mapping(target = "dateOfDeathFrom", ignore = true),
			@Mapping(target = "dateOfDeathTo", ignore = true),
			@Mapping(target = "placeOfBirth", ignore = true),
			@Mapping(target = "placeOfDeath", ignore = true),
			@Mapping(target = "sort", ignore = true),
			@Mapping(target = "animalPerformer", ignore = true),
			@Mapping(target = "disPerformer", ignore = true),
			@Mapping(target = "ds9Performer", ignore = true),
			@Mapping(target = "entPerformer", ignore = true),
			@Mapping(target = "filmPerformer", ignore = true),
			@Mapping(target = "standInPerformer", ignore = true),
			@Mapping(target = "stuntPerformer", ignore = true),
			@Mapping(target = "tasPerformer", ignore = true),
			@Mapping(target = "tngPerformer", ignore = true),
			@Mapping(target = "tosPerformer", ignore = true),
			@Mapping(target = "videoGamePerformer", ignore = true),
			@Mapping(target = "voicePerformer", ignore = true),
			@Mapping(target = "voyPerformer", ignore = true)
	})
	PerformerRequestDTO mapFull(PerformerFullRequest performerFullRequest);

	@Mappings({
			@Mapping(target = "episodesPerformanceHeaders", source = "episodesPerformances"),
			@Mapping(target = "episodesStuntPerformanceHeaders", source = "episodesStuntPerformances"),
			@Mapping(target = "episodesStandInPerformanceHeaders", source = "episodesStandInPerformances"),
			@Mapping(target = "moviesPerformanceHeaders", source = "moviesPerformances"),
			@Mapping(target = "moviesStuntPerformanceHeaders", source = "moviesStuntPerformances"),
			@Mapping(target = "moviesStandInPerformanceHeaders", source = "moviesStandInPerformances"),
			@Mapping(target = "characterHeaders", source = "characters")
	})
	com.cezarykluczynski.stapi.client.v1.soap.PerformerFull mapFull(Performer performer);

}
