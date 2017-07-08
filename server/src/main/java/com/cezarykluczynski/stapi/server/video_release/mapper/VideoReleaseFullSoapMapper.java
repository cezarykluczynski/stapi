package com.cezarykluczynski.stapi.server.video_release.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFull;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFullRequest;
import com.cezarykluczynski.stapi.model.video_release.dto.VideoReleaseRequestDTO;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.content_language.mapper.ContentLanguageSoapMapper;
import com.cezarykluczynski.stapi.server.content_rating.mapper.ContentRatingSoapMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesBaseSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {ContentLanguageSoapMapper.class, ContentRatingSoapMapper.class, DateMapper.class,
		EnumMapper.class, SeriesBaseSoapMapper.class, SeriesBaseSoapMapper.class})
public interface VideoReleaseFullSoapMapper {

	@Mapping(target = "title", ignore = true)
	@Mapping(target = "yearFrom", ignore = true)
	@Mapping(target = "yearTo", ignore = true)
	@Mapping(target = "runTimeFrom", ignore = true)
	@Mapping(target = "runTimeTo", ignore = true)
	@Mapping(target = "sort", ignore = true)
	VideoReleaseRequestDTO mapFull(VideoReleaseFullRequest videoReleaseFullRequest);

	VideoReleaseFull mapFull(VideoRelease videoRelease);

}
