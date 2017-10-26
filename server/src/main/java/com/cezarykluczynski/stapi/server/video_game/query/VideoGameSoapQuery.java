package com.cezarykluczynski.stapi.server.video_game.query;

import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFullRequest;
import com.cezarykluczynski.stapi.model.video_game.dto.VideoGameRequestDTO;
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame;
import com.cezarykluczynski.stapi.model.video_game.repository.VideoGameRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameBaseSoapMapper;
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class VideoGameSoapQuery {

	private final VideoGameBaseSoapMapper videoGameBaseSoapMapper;

	private final VideoGameFullSoapMapper videoGameFullSoapMapper;

	private final PageMapper pageMapper;

	private final VideoGameRepository videoGameRepository;

	public VideoGameSoapQuery(VideoGameBaseSoapMapper videoGameBaseSoapMapper, VideoGameFullSoapMapper videoGameFullSoapMapper, PageMapper pageMapper,
			VideoGameRepository videoGameRepository) {
		this.videoGameBaseSoapMapper = videoGameBaseSoapMapper;
		this.videoGameFullSoapMapper = videoGameFullSoapMapper;
		this.pageMapper = pageMapper;
		this.videoGameRepository = videoGameRepository;
	}

	public Page<VideoGame> query(VideoGameBaseRequest videoGameBaseRequest) {
		VideoGameRequestDTO videoGameRequestDTO = videoGameBaseSoapMapper.mapBase(videoGameBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(videoGameBaseRequest.getPage());
		return videoGameRepository.findMatching(videoGameRequestDTO, pageRequest);
	}

	public Page<VideoGame> query(VideoGameFullRequest videoGameFullRequest) {
		VideoGameRequestDTO videoGameRequestDTO = videoGameFullSoapMapper.mapFull(videoGameFullRequest);
		return videoGameRepository.findMatching(videoGameRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
