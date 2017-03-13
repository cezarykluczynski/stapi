package com.cezarykluczynski.stapi.server.episode.reader;


import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeResponse;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.episode.dto.EpisodeRestBeanParams;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeRestMapper;
import com.cezarykluczynski.stapi.server.episode.query.EpisodeRestQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class EpisodeRestReader implements BaseReader<EpisodeRestBeanParams, EpisodeResponse> {

	private EpisodeRestQuery episodeRestQuery;

	private EpisodeRestMapper episodeRestMapper;

	private PageMapper pageMapper;

	@Inject
	public EpisodeRestReader(EpisodeRestQuery episodeRestQuery, EpisodeRestMapper episodeRestMapper, PageMapper pageMapper) {
		this.episodeRestQuery = episodeRestQuery;
		this.episodeRestMapper = episodeRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public EpisodeResponse readBase(EpisodeRestBeanParams input) {
		Page<Episode> episodePage = episodeRestQuery.query(input);
		EpisodeResponse episodeResponse = new EpisodeResponse();
		episodeResponse.setPage(pageMapper.fromPageToRestResponsePage(episodePage));
		episodeResponse.getEpisodes().addAll(episodeRestMapper.map(episodePage.getContent()));
		return episodeResponse;
	}

}
