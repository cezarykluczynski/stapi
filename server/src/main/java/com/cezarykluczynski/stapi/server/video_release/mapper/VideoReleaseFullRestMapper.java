package com.cezarykluczynski.stapi.server.video_release.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseFull;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.content_language.mapper.ContentLanguageRestMapper;
import com.cezarykluczynski.stapi.server.content_rating.mapper.ContentRatingRestMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesBaseRestMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {ContentLanguageRestMapper.class, ContentRatingRestMapper.class, DateMapper.class,
		EnumMapper.class, SeriesBaseRestMapper.class, SeriesBaseRestMapper.class})
public interface VideoReleaseFullRestMapper {

	VideoReleaseFull mapFull(VideoRelease videoRelease);

}
