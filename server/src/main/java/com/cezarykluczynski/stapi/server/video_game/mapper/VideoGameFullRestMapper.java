package com.cezarykluczynski.stapi.server.video_game.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameFull;
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.content_rating.mapper.ContentRatingRestMapper;
import com.cezarykluczynski.stapi.server.genre.mapper.GenreRestMapper;
import com.cezarykluczynski.stapi.server.platform.mapper.PlatformRestMapper;
import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceRestMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {CompanyBaseRestMapper.class, DateMapper.class, EnumMapper.class, PlatformRestMapper.class,
		GenreRestMapper.class, ContentRatingRestMapper.class, ReferenceRestMapper.class})
public interface VideoGameFullRestMapper {

	VideoGameFull mapFull(VideoGame videoGame);

}
