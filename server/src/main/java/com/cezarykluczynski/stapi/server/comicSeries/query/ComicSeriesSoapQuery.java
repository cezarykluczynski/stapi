package com.cezarykluczynski.stapi.server.comicSeries.query;

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesRequest;
import com.cezarykluczynski.stapi.model.comicSeries.dto.ComicSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.comicSeries.repository.ComicSeriesRepository;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicSeriesSoapQuery {

	private ComicSeriesSoapMapper comicSeriesSoapMapper;

	private PageMapper pageMapper;

	private ComicSeriesRepository comicSeriesRepository;

	@Inject
	public ComicSeriesSoapQuery(ComicSeriesSoapMapper comicSeriesSoapMapper, PageMapper pageMapper, ComicSeriesRepository comicSeriesRepository) {
		this.comicSeriesSoapMapper = comicSeriesSoapMapper;
		this.pageMapper = pageMapper;
		this.comicSeriesRepository = comicSeriesRepository;
	}

	public Page<ComicSeries> query(ComicSeriesRequest comicSeriesRequest) {
		ComicSeriesRequestDTO comicSeriesRequestDTO = comicSeriesSoapMapper.map(comicSeriesRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(comicSeriesRequest.getPage());
		return comicSeriesRepository.findMatching(comicSeriesRequestDTO, pageRequest);
	}

}
