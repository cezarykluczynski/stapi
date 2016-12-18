package com.cezarykluczynski.stapi.server.performer.mapper;

import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterHeaderRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeHeaderRestMapper;
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterHeaderRestMapper.class, DateMapper.class,
		EnumMapper.class, EpisodeHeaderRestMapper.class, RequestSortRestMapper.class})
public interface PerformerRestMapper {

	PerformerRequestDTO map(PerformerRestBeanParams performerRestBeanParams);

	@Mappings({
			@Mapping(source = "performances", target = "performanceHeaders"),
			@Mapping(source = "stuntPerformances", target = "stuntPerformanceHeaders"),
			@Mapping(source = "standInPerformances", target = "standInPerformanceHeaders"),
			@Mapping(source = "characters", target = "characterHeaders")
	})
	com.cezarykluczynski.stapi.client.v1.rest.model.Performer map(Performer performer);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.Performer> map(List<Performer> performerList);

}
