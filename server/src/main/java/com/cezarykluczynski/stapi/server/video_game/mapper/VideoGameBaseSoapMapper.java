package com.cezarykluczynski.stapi.server.video_game.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBase;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBaseRequest;
import com.cezarykluczynski.stapi.model.video_game.dto.VideoGameRequestDTO;
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EnumMapper.class, RequestSortSoapMapper.class})
public interface VideoGameBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	@Mapping(source = "releaseDate.from", target = "releaseDateFrom")
	@Mapping(source = "releaseDate.to", target = "releaseDateTo")
	VideoGameRequestDTO mapBase(VideoGameBaseRequest videoGameBaseRequest);

	VideoGameBase mapBase(VideoGame videoGame);

	List<VideoGameBase> mapBase(List<VideoGame> videoGameList);

}
