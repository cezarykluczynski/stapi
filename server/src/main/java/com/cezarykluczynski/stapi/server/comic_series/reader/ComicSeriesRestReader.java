package com.cezarykluczynski.stapi.server.comic_series.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesFullResponse;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.server.comic_series.dto.ComicSeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesFullRestMapper;
import com.cezarykluczynski.stapi.server.comic_series.query.ComicSeriesRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ComicSeriesRestReader implements BaseReader<ComicSeriesRestBeanParams, ComicSeriesBaseResponse>,
		FullReader<String, ComicSeriesFullResponse> {

	private final ComicSeriesRestQuery comicSeriesRestQuery;

	private final ComicSeriesBaseRestMapper comicSeriesBaseRestMapper;

	private final ComicSeriesFullRestMapper comicSeriesFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public ComicSeriesRestReader(ComicSeriesRestQuery comicSeriesRestQuery, ComicSeriesBaseRestMapper comicSeriesBaseRestMapper,
			ComicSeriesFullRestMapper comicSeriesFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.comicSeriesRestQuery = comicSeriesRestQuery;
		this.comicSeriesBaseRestMapper = comicSeriesBaseRestMapper;
		this.comicSeriesFullRestMapper = comicSeriesFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public ComicSeriesBaseResponse readBase(ComicSeriesRestBeanParams input) {
		Page<ComicSeries> comicSeriesPage = comicSeriesRestQuery.query(input);
		ComicSeriesBaseResponse comicSeriesResponse = new ComicSeriesBaseResponse();
		comicSeriesResponse.setPage(pageMapper.fromPageToRestResponsePage(comicSeriesPage));
		comicSeriesResponse.setSort(sortMapper.map(input.getSort()));
		comicSeriesResponse.getComicSeries().addAll(comicSeriesBaseRestMapper.mapBase(comicSeriesPage.getContent()));
		return comicSeriesResponse;
	}

	@Override
	public ComicSeriesFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		ComicSeriesRestBeanParams comicSeriesRestBeanParams = new ComicSeriesRestBeanParams();
		comicSeriesRestBeanParams.setUid(uid);
		Page<ComicSeries> comicSeriesPage = comicSeriesRestQuery.query(comicSeriesRestBeanParams);
		ComicSeriesFullResponse comicSeriesResponse = new ComicSeriesFullResponse();
		comicSeriesResponse.setComicSeries(comicSeriesFullRestMapper.mapFull(Iterables.getOnlyElement(comicSeriesPage.getContent(), null)));
		return comicSeriesResponse;
	}

}
