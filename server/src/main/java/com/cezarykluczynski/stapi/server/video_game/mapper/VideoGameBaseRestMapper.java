package com.cezarykluczynski.stapi.server.video_game.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameBase;
import com.cezarykluczynski.stapi.model.video_game.dto.VideoGameRequestDTO;
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.video_game.dto.VideoGameRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EnumMapper.class, RequestSortRestMapper.class})
public interface VideoGameBaseRestMapper {

	VideoGameRequestDTO mapBase(VideoGameRestBeanParams videoGameRestBeanParams);

	VideoGameBase mapBase(VideoGame videoGame);

	List<VideoGameBase> mapBase(List<VideoGame> videoGameList);

}
