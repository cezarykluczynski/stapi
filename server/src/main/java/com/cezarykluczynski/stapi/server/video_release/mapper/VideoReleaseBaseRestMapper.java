package com.cezarykluczynski.stapi.server.video_release.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseBase;
import com.cezarykluczynski.stapi.model.video_release.dto.VideoReleaseRequestDTO;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.video_release.dto.VideoReleaseRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EnumMapper.class, RequestSortRestMapper.class})
public interface VideoReleaseBaseRestMapper {

	VideoReleaseRequestDTO mapBase(VideoReleaseRestBeanParams videoReleaseRestBeanParams);

	VideoReleaseBase mapBase(VideoRelease videoRelease);

	List<VideoReleaseBase> mapBase(List<VideoRelease> videoReleaseList);

}
