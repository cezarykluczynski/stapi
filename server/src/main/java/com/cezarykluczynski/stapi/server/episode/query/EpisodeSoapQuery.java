package com.cezarykluczynski.stapi.server.episode.query;

import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullRequest;
import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.episode.repository.EpisodeRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseSoapMapper;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class EpisodeSoapQuery {

	private final EpisodeBaseSoapMapper episodeBaseSoapMapper;

	private final EpisodeFullSoapMapper episodeFullSoapMapper;

	private final PageMapper pageMapper;

	private final EpisodeRepository episodeRepository;

	public EpisodeSoapQuery(EpisodeBaseSoapMapper episodeBaseSoapMapper, EpisodeFullSoapMapper episodeFullSoapMapper, PageMapper pageMapper,
			EpisodeRepository episodeRepository) {
		this.episodeBaseSoapMapper = episodeBaseSoapMapper;
		this.episodeFullSoapMapper = episodeFullSoapMapper;
		this.pageMapper = pageMapper;
		this.episodeRepository = episodeRepository;
	}

	public Page<Episode> query(EpisodeBaseRequest episodeBaseRequest) {
		EpisodeRequestDTO episodeRequestDTO = episodeBaseSoapMapper.mapBase(episodeBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(episodeBaseRequest.getPage());
		return episodeRepository.findMatching(episodeRequestDTO, pageRequest);
	}

	public Page<Episode> query(EpisodeFullRequest episodeFullRequest) {
		EpisodeRequestDTO episodeRequestDTO = episodeFullSoapMapper.mapFull(episodeFullRequest);
		return episodeRepository.findMatching(episodeRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
