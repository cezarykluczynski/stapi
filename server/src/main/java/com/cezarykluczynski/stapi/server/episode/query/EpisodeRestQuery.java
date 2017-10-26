package com.cezarykluczynski.stapi.server.episode.query;


import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.episode.repository.EpisodeRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.episode.dto.EpisodeRestBeanParams;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class EpisodeRestQuery {

	private final EpisodeBaseRestMapper episodeBaseRestMapper;

	private final PageMapper pageMapper;

	private final EpisodeRepository episodeRepository;

	public EpisodeRestQuery(EpisodeBaseRestMapper episodeBaseRestMapper, PageMapper pageMapper, EpisodeRepository episodeRepository) {
		this.episodeBaseRestMapper = episodeBaseRestMapper;
		this.pageMapper = pageMapper;
		this.episodeRepository = episodeRepository;
	}

	public Page<Episode> query(EpisodeRestBeanParams episodeRestBeanParams) {
		EpisodeRequestDTO episodeRequestDTO = episodeBaseRestMapper.mapBase(episodeRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(episodeRestBeanParams);
		return episodeRepository.findMatching(episodeRequestDTO, pageRequest);
	}

}
