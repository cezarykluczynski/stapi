package com.cezarykluczynski.stapi.server.video_release.reader;

import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFullResponse;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseBaseSoapMapper;
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseFullSoapMapper;
import com.cezarykluczynski.stapi.server.video_release.query.VideoReleaseSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class VideoReleaseSoapReader implements BaseReader<VideoReleaseBaseRequest, VideoReleaseBaseResponse>,
		FullReader<VideoReleaseFullRequest, VideoReleaseFullResponse> {

	private final VideoReleaseSoapQuery videoReleaseSoapQuery;

	private final VideoReleaseBaseSoapMapper videoReleaseBaseSoapMapper;

	private final VideoReleaseFullSoapMapper videoReleaseFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public VideoReleaseSoapReader(VideoReleaseSoapQuery videoReleaseSoapQuery, VideoReleaseBaseSoapMapper videoReleaseBaseSoapMapper,
			VideoReleaseFullSoapMapper videoReleaseFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.videoReleaseSoapQuery = videoReleaseSoapQuery;
		this.videoReleaseBaseSoapMapper = videoReleaseBaseSoapMapper;
		this.videoReleaseFullSoapMapper = videoReleaseFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public VideoReleaseBaseResponse readBase(VideoReleaseBaseRequest input) {
		Page<VideoRelease> videoReleasePage = videoReleaseSoapQuery.query(input);
		VideoReleaseBaseResponse videoReleaseResponse = new VideoReleaseBaseResponse();
		videoReleaseResponse.setPage(pageMapper.fromPageToSoapResponsePage(videoReleasePage));
		videoReleaseResponse.setSort(sortMapper.map(input.getSort()));
		videoReleaseResponse.getVideoReleases().addAll(videoReleaseBaseSoapMapper.mapBase(videoReleasePage.getContent()));
		return videoReleaseResponse;
	}

	@Override
	public VideoReleaseFullResponse readFull(VideoReleaseFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<VideoRelease> videoReleasePage = videoReleaseSoapQuery.query(input);
		VideoReleaseFullResponse videoReleaseFullResponse = new VideoReleaseFullResponse();
		videoReleaseFullResponse.setVideoRelease(videoReleaseFullSoapMapper.mapFull(Iterables.getOnlyElement(videoReleasePage.getContent(), null)));
		return videoReleaseFullResponse;
	}

}
