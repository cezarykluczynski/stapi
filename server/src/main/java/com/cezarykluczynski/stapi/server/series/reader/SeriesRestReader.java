package com.cezarykluczynski.stapi.server.series.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesFullResponse;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesFullRestMapper;
import com.cezarykluczynski.stapi.server.series.query.SeriesRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SeriesRestReader implements BaseReader<SeriesRestBeanParams, SeriesBaseResponse>, FullReader<String, SeriesFullResponse> {

	private final SeriesRestQuery seriesRestQuery;

	private final SeriesBaseRestMapper seriesBaseRestMapper;

	private final SeriesFullRestMapper seriesFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public SeriesRestReader(SeriesRestQuery seriesRestQuery, SeriesBaseRestMapper seriesBaseRestMapper, SeriesFullRestMapper seriesFullRestMapper,
			PageMapper pageMapper, SortMapper sortMapper) {
		this.seriesRestQuery = seriesRestQuery;
		this.seriesBaseRestMapper = seriesBaseRestMapper;
		this.seriesFullRestMapper = seriesFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public SeriesBaseResponse readBase(SeriesRestBeanParams input) {
		Page<Series> seriesPage = seriesRestQuery.query(input);
		SeriesBaseResponse seriesResponse = new SeriesBaseResponse();
		seriesResponse.setPage(pageMapper.fromPageToRestResponsePage(seriesPage));
		seriesResponse.setSort(sortMapper.map(input.getSort()));
		seriesResponse.getSeries().addAll(seriesBaseRestMapper.mapBase(seriesPage.getContent()));
		return seriesResponse;
	}

	@Override
	public SeriesFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		SeriesRestBeanParams seriesRestBeanParams = new SeriesRestBeanParams();
		seriesRestBeanParams.setUid(uid);
		Page<Series> seriesPage = seriesRestQuery.query(seriesRestBeanParams);
		SeriesFullResponse seriesResponse = new SeriesFullResponse();
		seriesResponse.setSeries(seriesFullRestMapper.mapFull(Iterables.getOnlyElement(seriesPage.getContent(), null)));
		return seriesResponse;
	}

}
