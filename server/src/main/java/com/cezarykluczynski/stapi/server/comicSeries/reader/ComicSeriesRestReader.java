package com.cezarykluczynski.stapi.server.comicSeries.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesFullResponse;
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries;
import com.cezarykluczynski.stapi.server.comicSeries.dto.ComicSeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesFullRestMapper;
import com.cezarykluczynski.stapi.server.comicSeries.query.ComicSeriesRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicSeriesRestReader implements BaseReader<ComicSeriesRestBeanParams, ComicSeriesBaseResponse>,
		FullReader<String, ComicSeriesFullResponse> {

	private ComicSeriesRestQuery comicSeriesRestQuery;

	private ComicSeriesBaseRestMapper comicSeriesBaseRestMapper;

	private ComicSeriesFullRestMapper comicSeriesFullRestMapper;

	private PageMapper pageMapper;

	@Inject
	public ComicSeriesRestReader(ComicSeriesRestQuery comicSeriesRestQuery, ComicSeriesBaseRestMapper comicSeriesBaseRestMapper,
			ComicSeriesFullRestMapper comicSeriesFullRestMapper, PageMapper pageMapper) {
		this.comicSeriesRestQuery = comicSeriesRestQuery;
		this.comicSeriesBaseRestMapper = comicSeriesBaseRestMapper;
		this.comicSeriesFullRestMapper = comicSeriesFullRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public ComicSeriesBaseResponse readBase(ComicSeriesRestBeanParams comicSeriesRestBeanParams) {
		Page<ComicSeries> comicSeriesPage = comicSeriesRestQuery.query(comicSeriesRestBeanParams);
		ComicSeriesBaseResponse comicSeriesResponse = new ComicSeriesBaseResponse();
		comicSeriesResponse.setPage(pageMapper.fromPageToRestResponsePage(comicSeriesPage));
		comicSeriesResponse.getComicSeries().addAll(comicSeriesBaseRestMapper.mapBase(comicSeriesPage.getContent()));
		return comicSeriesResponse;
	}

	@Override
	public ComicSeriesFullResponse readFull(String guid) {
		StaticValidator.requireGuid(guid);
		ComicSeriesRestBeanParams comicSeriesRestBeanParams = new ComicSeriesRestBeanParams();
		comicSeriesRestBeanParams.setGuid(guid);
		Page<ComicSeries> comicSeriesPage = comicSeriesRestQuery.query(comicSeriesRestBeanParams);
		ComicSeriesFullResponse comicSeriesResponse = new ComicSeriesFullResponse();
		comicSeriesResponse.setComicSeries(comicSeriesFullRestMapper.mapFull(Iterables.getOnlyElement(comicSeriesPage.getContent(), null)));
		return comicSeriesResponse;
	}

}
