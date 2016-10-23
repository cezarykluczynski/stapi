package com.cezarykluczynski.stapi.server.series.reader;

import com.cezarykluczynski.stapi.client.rest.model.Series;
import com.cezarykluczynski.stapi.client.soap.ResponsePage;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.ListReader;
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesRestMapper;
import com.cezarykluczynski.stapi.server.series.query.SeriesQueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class SeriesRestReader implements ListReader<SeriesRestBeanParams, Series> {

	private SeriesQueryBuilder seriesQueryBuilder;

	private SeriesRestMapper seriesRestMapper;

	private PageMapper pageMapper;

	@Inject
	public SeriesRestReader(SeriesQueryBuilder seriesQueryBuilder, SeriesRestMapper seriesRestMapper, PageMapper pageMapper) {
		this.seriesQueryBuilder = seriesQueryBuilder;
		this.seriesRestMapper = seriesRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public List<Series> search(SeriesRestBeanParams seriesRestBeanParams) {
		return toResponse(seriesRestBeanParams);
	}

	@Override
	public List<Series> getAll() {
		return toResponse(new SeriesRestBeanParams());
	}

	private List<Series> toResponse(SeriesRestBeanParams seriesRestBeanParams) {
		Page<com.cezarykluczynski.stapi.model.series.entity.Series> seriesPage = seriesQueryBuilder
				.query(seriesRestBeanParams);
		List<Series> seriesList = seriesRestMapper.map(seriesPage.getContent());
		ResponsePage responsePage = pageMapper.toResponsePage(seriesPage);
		// TODO: add pager
		return seriesList;
	}

}
