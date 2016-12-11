package com.cezarykluczynski.stapi.server.episode.query;

import com.cezarykluczynski.stapi.client.v1.soap.EpisodeRequest;
import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.episode.repository.EpisodeRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class EpisodeSoapQuery {

	private EpisodeSoapMapper episodeSoapMapper;

	private PageMapper pageMapper;

	private EpisodeRepository episodeRepository;

	@Inject
	public EpisodeSoapQuery(EpisodeSoapMapper episodeSoapMapper, PageMapper pageMapper,
			EpisodeRepository episodeRepository) {
		this.episodeSoapMapper = episodeSoapMapper;
		this.pageMapper = pageMapper;
		this.episodeRepository = episodeRepository;
	}

	public Page<Episode> query(EpisodeRequest episodeRequest) {
		EpisodeRequestDTO episodeRequestDTO = episodeSoapMapper.map(episodeRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(episodeRequest.getPage());
		return episodeRepository.findMatching(episodeRequestDTO, pageRequest);
	}

}
