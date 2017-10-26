package com.cezarykluczynski.stapi.server.video_game.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameFullResponse;
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.video_game.dto.VideoGameRestBeanParams;
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameBaseRestMapper;
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameFullRestMapper;
import com.cezarykluczynski.stapi.server.video_game.query.VideoGameRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class VideoGameRestReader implements BaseReader<VideoGameRestBeanParams, VideoGameBaseResponse>, FullReader<String, VideoGameFullResponse> {

	private final VideoGameRestQuery videoGameRestQuery;

	private final VideoGameBaseRestMapper videoGameBaseRestMapper;

	private final VideoGameFullRestMapper videoGameFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public VideoGameRestReader(VideoGameRestQuery videoGameRestQuery, VideoGameBaseRestMapper videoGameBaseRestMapper,
			VideoGameFullRestMapper videoGameFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.videoGameRestQuery = videoGameRestQuery;
		this.videoGameBaseRestMapper = videoGameBaseRestMapper;
		this.videoGameFullRestMapper = videoGameFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public VideoGameBaseResponse readBase(VideoGameRestBeanParams input) {
		Page<VideoGame> videoGamePage = videoGameRestQuery.query(input);
		VideoGameBaseResponse videoGameResponse = new VideoGameBaseResponse();
		videoGameResponse.setPage(pageMapper.fromPageToRestResponsePage(videoGamePage));
		videoGameResponse.setSort(sortMapper.map(input.getSort()));
		videoGameResponse.getVideoGames().addAll(videoGameBaseRestMapper.mapBase(videoGamePage.getContent()));
		return videoGameResponse;
	}

	@Override
	public VideoGameFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		VideoGameRestBeanParams videoGameRestBeanParams = new VideoGameRestBeanParams();
		videoGameRestBeanParams.setUid(uid);
		Page<VideoGame> videoGamePage = videoGameRestQuery.query(videoGameRestBeanParams);
		VideoGameFullResponse videoGameResponse = new VideoGameFullResponse();
		videoGameResponse.setVideoGame(videoGameFullRestMapper.mapFull(Iterables.getOnlyElement(videoGamePage.getContent(), null)));
		return videoGameResponse;
	}

}
