package com.cezarykluczynski.stapi.server.series.query;

import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.series.repository.SeriesRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesRequestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SeriesRestQuery {

	private SeriesRequestMapper seriesRequestMapper;

	private PageMapper pageMapper;

	private SeriesRepository seriesRepository;

	@Inject
	public SeriesRestQuery(SeriesRequestMapper seriesRequestMapper, PageMapper pageMapper,
			SeriesRepository seriesRepository) {
		this.seriesRequestMapper = seriesRequestMapper;
		this.pageMapper = pageMapper;
		this.seriesRepository = seriesRepository;
	}

	public Page<Series> query(SeriesRestBeanParams seriesRestBeanParams) {
		SeriesRequestDTO seriesRequestDTO = seriesRequestMapper.map(seriesRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageAwareBeanParamsToPageRequest(seriesRestBeanParams);
		return seriesRepository.findMatching(seriesRequestDTO, pageRequest);
	}

}