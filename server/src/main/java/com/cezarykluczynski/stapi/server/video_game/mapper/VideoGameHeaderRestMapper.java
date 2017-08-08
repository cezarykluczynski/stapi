package com.cezarykluczynski.stapi.server.video_game.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameHeader;
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VideoGameHeaderRestMapper {

	VideoGameHeader map(VideoGame videoGame);

	List<VideoGameHeader> map(List<VideoGame> videoGame);

}
