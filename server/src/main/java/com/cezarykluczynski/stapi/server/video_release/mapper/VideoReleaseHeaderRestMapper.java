package com.cezarykluczynski.stapi.server.video_release.mapper;

import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseHeader;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VideoReleaseHeaderRestMapper {

	VideoReleaseHeader map(VideoRelease videoRelease);

	List<VideoReleaseHeader> map(List<VideoRelease> videoRelease);

}
