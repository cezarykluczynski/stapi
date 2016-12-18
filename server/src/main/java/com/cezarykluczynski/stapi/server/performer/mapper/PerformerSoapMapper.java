package com.cezarykluczynski.stapi.server.performer.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.PerformerRequest;
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeHeaderSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterHeaderSoapMapper.class, DateMapper.class,
		EnumMapper.class, EpisodeHeaderSoapMapper.class, RequestSortSoapMapper.class})
public interface PerformerSoapMapper {

	@Mappings({
			@Mapping(source = "dateOfBirth.from", target = "dateOfBirthFrom"),
			@Mapping(source = "dateOfBirth.to", target = "dateOfBirthTo"),
			@Mapping(source = "dateOfDeath.from", target = "dateOfDeathFrom"),
			@Mapping(source = "dateOfDeath.to", target = "dateOfDeathTo")
	})
	PerformerRequestDTO map(PerformerRequest performerRequest);

	@Mappings({
			@Mapping(source = "performances", target = "performanceHeaders"),
			@Mapping(source = "stuntPerformances", target = "stuntPerformanceHeaders"),
			@Mapping(source = "standInPerformances", target = "standInPerformanceHeaders"),
			@Mapping(source = "characters", target = "characterHeaders")
	})
	com.cezarykluczynski.stapi.client.v1.soap.Performer map(Performer performer);

	List<com.cezarykluczynski.stapi.client.v1.soap.Performer> map(List<Performer> performerList);

}
