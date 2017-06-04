package com.cezarykluczynski.stapi.server.episode.reader;

import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullResponse;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseSoapMapper;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeFullSoapMapper;
import com.cezarykluczynski.stapi.server.episode.query.EpisodeSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class EpisodeSoapReader implements BaseReader<EpisodeBaseRequest, EpisodeBaseResponse>, FullReader<EpisodeFullRequest, EpisodeFullResponse> {

	private final EpisodeSoapQuery episodeSoapQuery;

	private final EpisodeBaseSoapMapper episodeBaseSoapMapper;

	private final EpisodeFullSoapMapper episodeFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public EpisodeSoapReader(EpisodeSoapQuery episodeSoapQuery, EpisodeBaseSoapMapper episodeBaseSoapMapper,
			EpisodeFullSoapMapper episodeFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.episodeSoapQuery = episodeSoapQuery;
		this.episodeBaseSoapMapper = episodeBaseSoapMapper;
		this.episodeFullSoapMapper = episodeFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public EpisodeBaseResponse readBase(EpisodeBaseRequest input) {
		Page<Episode> episodePage = episodeSoapQuery.query(input);
		EpisodeBaseResponse episodeResponse = new EpisodeBaseResponse();
		episodeResponse.setPage(pageMapper.fromPageToSoapResponsePage(episodePage));
		episodeResponse.setSort(sortMapper.map(input.getSort()));
		episodeResponse.getEpisodes().addAll(episodeBaseSoapMapper.mapBase(episodePage.getContent()));
		return episodeResponse;
	}

	@Override
	public EpisodeFullResponse readFull(EpisodeFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Episode> episodePage = episodeSoapQuery.query(input);
		EpisodeFullResponse episodeFullResponse = new EpisodeFullResponse();
		episodeFullResponse.setEpisode(episodeFullSoapMapper.mapFull(Iterables.getOnlyElement(episodePage.getContent(), null)));
		return episodeFullResponse;
	}

}
