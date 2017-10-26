package com.cezarykluczynski.stapi.server.video_game.query;

import com.cezarykluczynski.stapi.model.video_game.dto.VideoGameRequestDTO;
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame;
import com.cezarykluczynski.stapi.model.video_game.repository.VideoGameRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.video_game.dto.VideoGameRestBeanParams;
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class VideoGameRestQuery {

	private final VideoGameBaseRestMapper videoGameBaseRestMapper;

	private final PageMapper pageMapper;

	private final VideoGameRepository videoGameRepository;

	public VideoGameRestQuery(VideoGameBaseRestMapper videoGameBaseRestMapper, PageMapper pageMapper, VideoGameRepository videoGameRepository) {
		this.videoGameBaseRestMapper = videoGameBaseRestMapper;
		this.pageMapper = pageMapper;
		this.videoGameRepository = videoGameRepository;
	}

	public Page<VideoGame> query(VideoGameRestBeanParams videoGameRestBeanParams) {
		VideoGameRequestDTO videoGameRequestDTO = videoGameBaseRestMapper.mapBase(videoGameRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(videoGameRestBeanParams);
		return videoGameRepository.findMatching(videoGameRequestDTO, pageRequest);
	}

}
