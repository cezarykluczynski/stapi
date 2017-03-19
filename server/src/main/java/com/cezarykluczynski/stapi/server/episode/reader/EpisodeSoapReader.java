package com.cezarykluczynski.stapi.server.episode.reader;

import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullResponse;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeSoapMapper;
import com.cezarykluczynski.stapi.server.episode.query.EpisodeSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class EpisodeSoapReader implements BaseReader<EpisodeBaseRequest, EpisodeBaseResponse>, FullReader<EpisodeFullRequest, EpisodeFullResponse> {

	private EpisodeSoapQuery episodeSoapQuery;

	private EpisodeSoapMapper episodeSoapMapper;

	private PageMapper pageMapper;

	public EpisodeSoapReader(EpisodeSoapQuery episodeSoapQuery, EpisodeSoapMapper episodeSoapMapper, PageMapper pageMapper) {
		this.episodeSoapQuery = episodeSoapQuery;
		this.episodeSoapMapper = episodeSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public EpisodeBaseResponse readBase(EpisodeBaseRequest input) {
		Page<Episode> episodePage = episodeSoapQuery.query(input);
		EpisodeBaseResponse episodeResponse = new EpisodeBaseResponse();
		episodeResponse.setPage(pageMapper.fromPageToSoapResponsePage(episodePage));
		episodeResponse.getEpisodes().addAll(episodeSoapMapper.mapBase(episodePage.getContent()));
		return episodeResponse;
	}

	@Override
	public EpisodeFullResponse readFull(EpisodeFullRequest input) {
		Page<Episode> episodePage = episodeSoapQuery.query(input);
		EpisodeFullResponse episodeFullResponse = new EpisodeFullResponse();
		episodeFullResponse.setEpisode(episodeSoapMapper.mapFull(Iterables.getOnlyElement(episodePage.getContent(), null)));
		return episodeFullResponse;
	}

}
