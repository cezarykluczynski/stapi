package com.cezarykluczynski.stapi.server.video_release.query;

import com.cezarykluczynski.stapi.model.video_release.dto.VideoReleaseRequestDTO;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import com.cezarykluczynski.stapi.model.video_release.repository.VideoReleaseRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.video_release.dto.VideoReleaseRestBeanParams;
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class VideoReleaseRestQuery {

	private final VideoReleaseBaseRestMapper videoReleaseBaseRestMapper;

	private final PageMapper pageMapper;

	private final VideoReleaseRepository videoReleaseRepository;

	public VideoReleaseRestQuery(VideoReleaseBaseRestMapper videoReleaseBaseRestMapper, PageMapper pageMapper,
			VideoReleaseRepository videoReleaseRepository) {
		this.videoReleaseBaseRestMapper = videoReleaseBaseRestMapper;
		this.pageMapper = pageMapper;
		this.videoReleaseRepository = videoReleaseRepository;
	}

	public Page<VideoRelease> query(VideoReleaseRestBeanParams videoReleaseRestBeanParams) {
		VideoReleaseRequestDTO videoReleaseRequestDTO = videoReleaseBaseRestMapper.mapBase(videoReleaseRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(videoReleaseRestBeanParams);
		return videoReleaseRepository.findMatching(videoReleaseRequestDTO, pageRequest);
	}

}
