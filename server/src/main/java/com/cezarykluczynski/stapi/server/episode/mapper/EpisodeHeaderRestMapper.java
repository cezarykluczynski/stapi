package com.cezarykluczynski.stapi.server.episode.mapper;


import com.cezarykluczynski.stapi.client.rest.model.EpisodeHeader;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EpisodeHeaderRestMapper {

	EpisodeHeader map(Episode episode);

	List<EpisodeHeader> map(List<Episode> episode);

}
