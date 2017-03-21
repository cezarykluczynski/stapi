package com.cezarykluczynski.stapi.server.comicSeries.query;

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullRequest;
import com.cezarykluczynski.stapi.model.comicSeries.dto.ComicSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.comicSeries.repository.ComicSeriesRepository;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicSeriesSoapQuery {

	private ComicSeriesBaseSoapMapper comicSeriesBaseSoapMapper;

	private ComicSeriesFullSoapMapper comicSeriesFullSoapMapper;

	private PageMapper pageMapper;

	private ComicSeriesRepository comicSeriesRepository;

	@Inject
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
