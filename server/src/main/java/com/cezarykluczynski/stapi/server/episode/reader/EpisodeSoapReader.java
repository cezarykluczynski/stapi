package com.cezarykluczynski.stapi.server.episode.reader;

import com.cezarykluczynski.stapi.client.v1.soap.EpisodeRequest;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeResponse;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeSoapMapper;
import com.cezarykluczynski.stapi.server.episode.query.EpisodeSoapQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class EpisodeSoapReader implements BaseReader<EpisodeRequest, EpisodeResponse> {

	private EpisodeSoapQuery episodeSoapQuery;

	private EpisodeSoapMapper episodeSoapMapper;

	private PageMapper pageMapper;

	public EpisodeSoapReader(EpisodeSoapQuery episodeSoapQuery, EpisodeSoapMapper episodeSoapMapper, PageMapper pageMapper) {
		this.episodeSoapQuery = episodeSoapQuery;
		this.episodeSoapMapper = episodeSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public EpisodeResponse readBase(EpisodeRequest input) {
		Page<Episode> episodePage = episodeSoapQuery.query(input);
		EpisodeResponse episodeResponse = new EpisodeResponse();
		episodeResponse.setPage(pageMapper.fromPageToSoapResponsePage(episodePage));
		episodeResponse.getEpisodes().addAll(episodeSoapMapper.map(episodePage.getContent()));
		return episodeResponse;
	}

}
