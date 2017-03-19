package com.cezarykluczynski.stapi.server.episode.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeFullResponse;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.episode.dto.EpisodeRestBeanParams;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeRestMapper;
import com.cezarykluczynski.stapi.server.episode.query.EpisodeRestQuery;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class EpisodeRestReader implements BaseReader<EpisodeRestBeanParams, EpisodeBaseResponse>, FullReader<String, EpisodeFullResponse> {

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
	public EpisodeBaseResponse readBase(EpisodeRestBeanParams episodeRestBeanParams) {
		Page<Episode> episodePage = episodeRestQuery.query(episodeRestBeanParams);
		EpisodeBaseResponse episodeResponse = new EpisodeBaseResponse();
		episodeResponse.setPage(pageMapper.fromPageToRestResponsePage(episodePage));
		episodeResponse.getEpisodes().addAll(episodeRestMapper.mapBase(episodePage.getContent()));
		return episodeResponse;
	}

	@Override
	public EpisodeFullResponse readFull(String guid) {
		Preconditions.checkNotNull(guid, "GUID is required");
		EpisodeRestBeanParams episodeRestBeanParams = new EpisodeRestBeanParams();
		episodeRestBeanParams.setGuid(guid);
		Page<Episode> episodePage = episodeRestQuery.query(episodeRestBeanParams);
		EpisodeFullResponse episodeResponse = new EpisodeFullResponse();
		episodeResponse.setEpisode(episodeRestMapper.mapFull(Iterables.getOnlyElement(episodePage.getContent(), null)));
		return episodeResponse;
	}

}
