package com.cezarykluczynski.stapi.server.series.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesResponse;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.Reader;
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesRestMapper;
import com.cezarykluczynski.stapi.server.series.query.SeriesQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SeriesRestReader implements Reader<SeriesRestBeanParams, SeriesResponse> {

	private SeriesQuery seriesQuery;

	private SeriesRestMapper seriesRestMapper;

	private PageMapper pageMapper;

	@Inject
	public SeriesRestReader(SeriesQuery seriesQuery, SeriesRestMapper seriesRestMapper, PageMapper pageMapper) {
		this.seriesQuery = seriesQuery;
		this.seriesRestMapper = seriesRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public SeriesResponse read(SeriesRestBeanParams seriesRestBeanParams) {
		return toResponse(seriesRestBeanParams);
	}

	private SeriesResponse toResponse(SeriesRestBeanParams seriesRestBeanParams) {
		Page<com.cezarykluczynski.stapi.model.series.entity.Series> seriesPage = seriesQuery
				.query(seriesRestBeanParams);
		SeriesResponse seriesResponse = new SeriesResponse();
		seriesResponse.setPage(pageMapper.fromPageToRestResponsePage(seriesPage));
		seriesResponse.getSeries().addAll(seriesRestMapper.map(seriesPage.getContent()));
		return seriesResponse;
	}

}
