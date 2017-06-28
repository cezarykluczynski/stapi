package com.cezarykluczynski.stapi.server.season.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.SeasonFull;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonFullRequest;
import com.cezarykluczynski.stapi.model.season.dto.SeasonRequestDTO;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface SeasonFullSoapMapper {

	@Mapping(target = "title", ignore = true)
	@Mapping(target = "seasonNumberFrom", ignore = true)
	@Mapping(target = "seasonNumberTo", ignore = true)
	@Mapping(target = "numberOfEpisodesFrom", ignore = true)
	@Mapping(target = "numberOfEpisodesTo", ignore = true)
	@Mapping(target = "sort", ignore = true)
	SeasonRequestDTO mapFull(SeasonFullRequest seasonFullRequest);

	SeasonFull mapFull(Season season);

}
