package com.cezarykluczynski.stapi.server.comic_series.query;

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullRequest;
import com.cezarykluczynski.stapi.model.comic_series.dto.ComicSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.comic_series.repository.ComicSeriesRepository;
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ComicSeriesSoapQuery {

	private final ComicSeriesBaseSoapMapper comicSeriesBaseSoapMapper;

	private final ComicSeriesFullSoapMapper comicSeriesFullSoapMapper;

	private final PageMapper pageMapper;

	private final ComicSeriesRepository comicSeriesRepository;

	public ComicSeriesSoapQuery(ComicSeriesBaseSoapMapper comicSeriesBaseSoapMapper, ComicSeriesFullSoapMapper comicSeriesFullSoapMapper,
			PageMapper pageMapper, ComicSeriesRepository comicSeriesRepository) {
		this.comicSeriesBaseSoapMapper = comicSeriesBaseSoapMapper;
		this.comicSeriesFullSoapMapper = comicSeriesFullSoapMapper;
		this.pageMapper = pageMapper;
		this.comicSeriesRepository = comicSeriesRepository;
	}

	public Page<ComicSeries> query(ComicSeriesBaseRequest comicSeriesBaseRequest) {
		ComicSeriesRequestDTO comicSeriesRequestDTO = comicSeriesBaseSoapMapper.mapBase(comicSeriesBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(comicSeriesBaseRequest.getPage());
		return comicSeriesRepository.findMatching(comicSeriesRequestDTO, pageRequest);
	}

	public Page<ComicSeries> query(ComicSeriesFullRequest comicSeriesFullRequest) {
		ComicSeriesRequestDTO comicSeriesRequestDTO = comicSeriesFullSoapMapper.mapFull(comicSeriesFullRequest);
		return comicSeriesRepository.findMatching(comicSeriesRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
