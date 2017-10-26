package com.cezarykluczynski.stapi.server.series.query;

import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullRequest;
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SeriesSoapQuery {

	private final SeriesBaseSoapMapper seriesBaseSoapMapper;

	private final SeriesFullSoapMapper seriesFullSoapMapper;

	private final PageMapper pageMapper;

	private final SeriesRepository seriesRepository;

	public SeriesSoapQuery(SeriesBaseSoapMapper seriesBaseSoapMapper, SeriesFullSoapMapper seriesFullSoapMapper, PageMapper pageMapper,
			SeriesRepository seriesRepository) {
		this.seriesBaseSoapMapper = seriesBaseSoapMapper;
		this.seriesFullSoapMapper = seriesFullSoapMapper;
		this.pageMapper = pageMapper;
		this.seriesRepository = seriesRepository;
	}

	public Page<Series> query(SeriesBaseRequest seriesBaseRequest) {
		SeriesRequestDTO seriesRequestDTO = seriesBaseSoapMapper.mapBase(seriesBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(seriesBaseRequest.getPage());
		return seriesRepository.findMatching(seriesRequestDTO, pageRequest);
	}

	public Page<Series> query(SeriesFullRequest seriesFullRequest) {
		SeriesRequestDTO seriesRequestDTO = seriesFullSoapMapper.mapFull(seriesFullRequest);
		return seriesRepository.findMatching(seriesRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
