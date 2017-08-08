package com.cezarykluczynski.stapi.server.video_game.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFull;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFullRequest;
import com.cezarykluczynski.stapi.model.video_game.dto.VideoGameRequestDTO;
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.content_rating.mapper.ContentRatingSoapMapper;
import com.cezarykluczynski.stapi.server.genre.mapper.GenreSoapMapper;
import com.cezarykluczynski.stapi.server.platform.mapper.PlatformSoapMapper;
import com.cezarykluczynski.stapi.server.reference.mapper.ReferenceSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CompanyBaseSoapMapper.class, DateMapper.class, EnumMapper.class, PlatformSoapMapper.class,
		GenreSoapMapper.class, ContentRatingSoapMapper.class, ReferenceSoapMapper.class})
public interface VideoGameFullSoapMapper {

	@Mapping(target = "title", ignore = true)
	@Mapping(target = "releaseDateFrom", ignore = true)
	@Mapping(target = "releaseDateTo", ignore = true)
	@Mapping(target = "sort", ignore = true)
	VideoGameRequestDTO mapFull(VideoGameFullRequest videoGameFullRequest);

	VideoGameFull mapFull(VideoGame videoGame);

}
