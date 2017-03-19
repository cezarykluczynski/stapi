package com.cezarykluczynski.stapi.server.episode.query;


import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.episode.repository.EpisodeRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.episode.dto.EpisodeRestBeanParams;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class EpisodeRestQuery {

	private EpisodeRestMapper episodeRestMapper;

	private PageMapper pageMapper;

	private EpisodeRepository episodeRepository;

	@Inject
	public EpisodeRestQuery(EpisodeRestMapper episodeRestMapper, PageMapper pageMapper, EpisodeRepository episodeRepository) {
		this.episodeRestMapper = episodeRestMapper;
		this.pageMapper = pageMapper;
		this.episodeRepository = episodeRepository;
	}

	public Page<Episode> query(EpisodeRestBeanParams episodeRestBeanParams) {
		EpisodeRequestDTO episodeRequestDTO = episodeRestMapper.mapBase(episodeRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(episodeRestBeanParams);
		return episodeRepository.findMatching(episodeRequestDTO, pageRequest);
	}

}
