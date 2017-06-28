package com.cezarykluczynski.stapi.server.season.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.SeasonBase;
import com.cezarykluczynski.stapi.client.v1.soap.SeasonBaseRequest;
import com.cezarykluczynski.stapi.model.season.dto.SeasonRequestDTO;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortSoapMapper.class})
public interface SeasonBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	@Mapping(source = "seasonNumber.from", target = "seasonNumberFrom")
	@Mapping(source = "seasonNumber.to", target = "seasonNumberTo")
	@Mapping(source = "numberOfEpisodes.from", target = "numberOfEpisodesFrom")
	@Mapping(source = "numberOfEpisodes.to", target = "numberOfEpisodesTo")
	SeasonRequestDTO mapBase(SeasonBaseRequest seasonBaseRequest);

	SeasonBase mapBase(Season season);

	List<SeasonBase> mapBase(List<Season> seasonList);

}
