package com.cezarykluczynski.stapi.server.series.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesFullResponse;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesRestMapper;
import com.cezarykluczynski.stapi.server.series.query.SeriesRestQuery;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SeriesRestReader implements BaseReader<SeriesRestBeanParams, SeriesBaseResponse>, FullReader<String, SeriesFullResponse> {

	private SeriesRestQuery seriesRestQuery;

	private SeriesRestMapper seriesRestMapper;

	private PageMapper pageMapper;

	@Inject
	public SeriesRestReader(SeriesRestQuery seriesRestQuery, SeriesRestMapper seriesRestMapper, PageMapper pageMapper) {
		this.seriesRestQuery = seriesRestQuery;
		this.seriesRestMapper = seriesRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public SeriesBaseResponse readBase(SeriesRestBeanParams seriesRestBeanParams) {
		Page<com.cezarykluczynski.stapi.model.series.entity.Series> seriesPage = seriesRestQuery
				.query(seriesRestBeanParams);
		SeriesBaseResponse seriesResponse = new SeriesBaseResponse();
		seriesResponse.setPage(pageMapper.fromPageToRestResponsePage(seriesPage));
		seriesResponse.getSeries().addAll(seriesRestMapper.mapBase(seriesPage.getContent()));
		return seriesResponse;
	}

	@Override
	public SeriesFullResponse readFull(String guid) {
		Preconditions.checkNotNull(guid, "GUID is required");
		SeriesRestBeanParams seriesRestBeanParams = new SeriesRestBeanParams();
		seriesRestBeanParams.setGuid(guid);
		Page<com.cezarykluczynski.stapi.model.series.entity.Series> seriesPage = seriesRestQuery.query(seriesRestBeanParams);
		SeriesFullResponse seriesResponse = new SeriesFullResponse();
		seriesResponse.setSeries(seriesRestMapper.mapFull(Iterables.getOnlyElement(seriesPage.getContent(), null)));
		return seriesResponse;
	}

}
