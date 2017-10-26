package com.cezarykluczynski.stapi.server.episode.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeFullResponse;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.episode.dto.EpisodeRestBeanParams;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseRestMapper;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeFullRestMapper;
import com.cezarykluczynski.stapi.server.episode.query.EpisodeRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class EpisodeRestReader implements BaseReader<EpisodeRestBeanParams, EpisodeBaseResponse>, FullReader<String, EpisodeFullResponse> {

	private final EpisodeRestQuery episodeRestQuery;

	private final EpisodeBaseRestMapper episodeBaseRestMapper;

	private final EpisodeFullRestMapper episodeFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public EpisodeRestReader(EpisodeRestQuery episodeRestQuery, EpisodeBaseRestMapper episodeBaseRestMapper,
			EpisodeFullRestMapper episodeFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.episodeRestQuery = episodeRestQuery;
		this.episodeBaseRestMapper = episodeBaseRestMapper;
		this.episodeFullRestMapper = episodeFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public EpisodeBaseResponse readBase(EpisodeRestBeanParams input) {
		Page<Episode> episodePage = episodeRestQuery.query(input);
		EpisodeBaseResponse episodeResponse = new EpisodeBaseResponse();
		episodeResponse.setPage(pageMapper.fromPageToRestResponsePage(episodePage));
		episodeResponse.setSort(sortMapper.map(input.getSort()));
		episodeResponse.getEpisodes().addAll(episodeBaseRestMapper.mapBase(episodePage.getContent()));
		return episodeResponse;
	}

	@Override
	public EpisodeFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		EpisodeRestBeanParams episodeRestBeanParams = new EpisodeRestBeanParams();
		episodeRestBeanParams.setUid(uid);
		Page<Episode> episodePage = episodeRestQuery.query(episodeRestBeanParams);
		EpisodeFullResponse episodeResponse = new EpisodeFullResponse();
		episodeResponse.setEpisode(episodeFullRestMapper.mapFull(Iterables.getOnlyElement(episodePage.getContent(), null)));
		return episodeResponse;
	}

}
