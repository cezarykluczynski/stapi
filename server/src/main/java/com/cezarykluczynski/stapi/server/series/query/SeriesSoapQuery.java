package com.cezarykluczynski.stapi.server.series.query;

import com.cezarykluczynski.stapi.client.v1.soap.SeriesRequest;
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SeriesSoapQuery {

	private SeriesSoapMapper seriesSoapMapper;

	private PageMapper pageMapper;

	private SeriesRepository seriesRepository;

	@Inject
	public SeriesSoapQuery(SeriesSoapMapper seriesSoapMapper, PageMapper pageMapper, SeriesRepository seriesRepository) {
		this.seriesSoapMapper = seriesSoapMapper;
		this.pageMapper = pageMapper;
		this.seriesRepository = seriesRepository;
	}

	public Page<Series> query(SeriesRequest seriesRequest) {
		SeriesRequestDTO seriesRequestDTO = seriesSoapMapper.map(seriesRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(seriesRequest.getPage());
		return seriesRepository.findMatching(seriesRequestDTO, pageRequest);
	}

}
