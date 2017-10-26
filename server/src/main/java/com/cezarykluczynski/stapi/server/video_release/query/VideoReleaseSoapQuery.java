package com.cezarykluczynski.stapi.server.video_release.query;

import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFullRequest;
import com.cezarykluczynski.stapi.model.video_release.dto.VideoReleaseRequestDTO;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import com.cezarykluczynski.stapi.model.video_release.repository.VideoReleaseRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseBaseSoapMapper;
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class VideoReleaseSoapQuery {

	private final VideoReleaseBaseSoapMapper videoReleaseBaseSoapMapper;

	private final VideoReleaseFullSoapMapper videoReleaseFullSoapMapper;

	private final PageMapper pageMapper;

	private final VideoReleaseRepository videoReleaseRepository;

	public VideoReleaseSoapQuery(VideoReleaseBaseSoapMapper videoReleaseBaseSoapMapper, VideoReleaseFullSoapMapper videoReleaseFullSoapMapper,
			PageMapper pageMapper, VideoReleaseRepository videoReleaseRepository) {
		this.videoReleaseBaseSoapMapper = videoReleaseBaseSoapMapper;
		this.videoReleaseFullSoapMapper = videoReleaseFullSoapMapper;
		this.pageMapper = pageMapper;
		this.videoReleaseRepository = videoReleaseRepository;
	}

	public Page<VideoRelease> query(VideoReleaseBaseRequest videoReleaseBaseRequest) {
		VideoReleaseRequestDTO videoReleaseRequestDTO = videoReleaseBaseSoapMapper.mapBase(videoReleaseBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(videoReleaseBaseRequest.getPage());
		return videoReleaseRepository.findMatching(videoReleaseRequestDTO, pageRequest);
	}

	public Page<VideoRelease> query(VideoReleaseFullRequest videoReleaseFullRequest) {
		VideoReleaseRequestDTO videoReleaseRequestDTO = videoReleaseFullSoapMapper.mapFull(videoReleaseFullRequest);
		return videoReleaseRepository.findMatching(videoReleaseRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
