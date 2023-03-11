package com.cezarykluczynski.stapi.server.video_release.reader;

import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.VideoReleaseV2FullResponse;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.video_release.dto.VideoReleaseV2RestBeanParams;
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseBaseRestMapper;
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseFullRestMapper;
import com.cezarykluczynski.stapi.server.video_release.query.VideoReleaseRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class VideoReleaseV2RestReader implements BaseReader<VideoReleaseV2RestBeanParams, VideoReleaseV2BaseResponse>,
		FullReader<VideoReleaseV2FullResponse> {

	private final VideoReleaseRestQuery videoReleaseRestQuery;

	private final VideoReleaseBaseRestMapper videoReleaseBaseRestMapper;

	private final VideoReleaseFullRestMapper videoReleaseFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public VideoReleaseV2RestReader(VideoReleaseRestQuery videoReleaseRestQuery, VideoReleaseBaseRestMapper videoReleaseBaseRestMapper,
			VideoReleaseFullRestMapper videoReleaseFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.videoReleaseRestQuery = videoReleaseRestQuery;
		this.videoReleaseBaseRestMapper = videoReleaseBaseRestMapper;
		this.videoReleaseFullRestMapper = videoReleaseFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public VideoReleaseV2BaseResponse readBase(VideoReleaseV2RestBeanParams input) {
		Page<VideoRelease> videoReleasePage = videoReleaseRestQuery.query(input);
		VideoReleaseV2BaseResponse videoReleaseResponse = new VideoReleaseV2BaseResponse();
		videoReleaseResponse.setPage(pageMapper.fromPageToRestResponsePage(videoReleasePage));
		videoReleaseResponse.setSort(sortMapper.map(input.getSort()));
		videoReleaseResponse.getVideoReleases().addAll(videoReleaseBaseRestMapper.mapV2Base(videoReleasePage.getContent()));
		return videoReleaseResponse;
	}

	@Override
	public VideoReleaseV2FullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		VideoReleaseV2RestBeanParams videoReleaseV2RestBeanParams = new VideoReleaseV2RestBeanParams();
		videoReleaseV2RestBeanParams.setUid(uid);
		Page<VideoRelease> videoReleasePage = videoReleaseRestQuery.query(videoReleaseV2RestBeanParams);
		VideoReleaseV2FullResponse videoReleaseResponse = new VideoReleaseV2FullResponse();
		videoReleaseResponse.setVideoRelease(videoReleaseFullRestMapper.mapV2Full(Iterables.getOnlyElement(videoReleasePage.getContent(), null)));
		return videoReleaseResponse;
	}

}
