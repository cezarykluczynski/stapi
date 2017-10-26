package com.cezarykluczynski.stapi.server.video_release.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseFullResponse;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.video_release.dto.VideoReleaseRestBeanParams;
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseBaseRestMapper;
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseFullRestMapper;
import com.cezarykluczynski.stapi.server.video_release.query.VideoReleaseRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class VideoReleaseRestReader implements BaseReader<VideoReleaseRestBeanParams, VideoReleaseBaseResponse>,
		FullReader<String, VideoReleaseFullResponse> {

	private final VideoReleaseRestQuery videoReleaseRestQuery;

	private final VideoReleaseBaseRestMapper videoReleaseBaseRestMapper;

	private final VideoReleaseFullRestMapper videoReleaseFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public VideoReleaseRestReader(VideoReleaseRestQuery videoReleaseRestQuery, VideoReleaseBaseRestMapper videoReleaseBaseRestMapper,
			VideoReleaseFullRestMapper videoReleaseFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.videoReleaseRestQuery = videoReleaseRestQuery;
		this.videoReleaseBaseRestMapper = videoReleaseBaseRestMapper;
		this.videoReleaseFullRestMapper = videoReleaseFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public VideoReleaseBaseResponse readBase(VideoReleaseRestBeanParams input) {
		Page<VideoRelease> videoReleasePage = videoReleaseRestQuery.query(input);
		VideoReleaseBaseResponse videoReleaseResponse = new VideoReleaseBaseResponse();
		videoReleaseResponse.setPage(pageMapper.fromPageToRestResponsePage(videoReleasePage));
		videoReleaseResponse.setSort(sortMapper.map(input.getSort()));
		videoReleaseResponse.getVideoReleases().addAll(videoReleaseBaseRestMapper.mapBase(videoReleasePage.getContent()));
		return videoReleaseResponse;
	}

	@Override
	public VideoReleaseFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		VideoReleaseRestBeanParams videoReleaseRestBeanParams = new VideoReleaseRestBeanParams();
		videoReleaseRestBeanParams.setUid(uid);
		Page<VideoRelease> videoReleasePage = videoReleaseRestQuery.query(videoReleaseRestBeanParams);
		VideoReleaseFullResponse videoReleaseResponse = new VideoReleaseFullResponse();
		videoReleaseResponse.setVideoRelease(videoReleaseFullRestMapper.mapFull(Iterables.getOnlyElement(videoReleasePage.getContent(), null)));
		return videoReleaseResponse;
	}

}
