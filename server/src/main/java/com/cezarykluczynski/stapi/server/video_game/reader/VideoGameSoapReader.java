package com.cezarykluczynski.stapi.server.video_game.reader;

import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFullResponse;
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameBaseSoapMapper;
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameFullSoapMapper;
import com.cezarykluczynski.stapi.server.video_game.query.VideoGameSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class VideoGameSoapReader implements BaseReader<VideoGameBaseRequest, VideoGameBaseResponse>,
		FullReader<VideoGameFullRequest, VideoGameFullResponse> {

	private final VideoGameSoapQuery videoGameSoapQuery;

	private final VideoGameBaseSoapMapper videoGameBaseSoapMapper;

	private final VideoGameFullSoapMapper videoGameFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public VideoGameSoapReader(VideoGameSoapQuery videoGameSoapQuery, VideoGameBaseSoapMapper videoGameBaseSoapMapper,
			VideoGameFullSoapMapper videoGameFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.videoGameSoapQuery = videoGameSoapQuery;
		this.videoGameBaseSoapMapper = videoGameBaseSoapMapper;
		this.videoGameFullSoapMapper = videoGameFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public VideoGameBaseResponse readBase(VideoGameBaseRequest input) {
		Page<VideoGame> videoGamePage = videoGameSoapQuery.query(input);
		VideoGameBaseResponse videoGameResponse = new VideoGameBaseResponse();
		videoGameResponse.setPage(pageMapper.fromPageToSoapResponsePage(videoGamePage));
		videoGameResponse.setSort(sortMapper.map(input.getSort()));
		videoGameResponse.getVideoGames().addAll(videoGameBaseSoapMapper.mapBase(videoGamePage.getContent()));
		return videoGameResponse;
	}

	@Override
	public VideoGameFullResponse readFull(VideoGameFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<VideoGame> videoGamePage = videoGameSoapQuery.query(input);
		VideoGameFullResponse videoGameFullResponse = new VideoGameFullResponse();
		videoGameFullResponse.setVideoGame(videoGameFullSoapMapper.mapFull(Iterables.getOnlyElement(videoGamePage.getContent(), null)));
		return videoGameFullResponse;
	}

}
