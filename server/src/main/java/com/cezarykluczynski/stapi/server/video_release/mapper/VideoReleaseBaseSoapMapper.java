package com.cezarykluczynski.stapi.server.video_release.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBase;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBaseRequest;
import com.cezarykluczynski.stapi.model.video_release.dto.VideoReleaseRequestDTO;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EnumMapper.class, RequestSortSoapMapper.class})
public interface VideoReleaseBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	@Mapping(source = "year.from", target = "yearFrom")
	@Mapping(source = "year.to", target = "yearTo")
	@Mapping(source = "runTime.from", target = "runTimeFrom")
	@Mapping(source = "runTime.to", target = "runTimeTo")
	VideoReleaseRequestDTO mapBase(VideoReleaseBaseRequest videoReleaseBaseRequest);

	VideoReleaseBase mapBase(VideoRelease videoRelease);

	List<VideoReleaseBase> mapBase(List<VideoRelease> videoReleaseList);

}
