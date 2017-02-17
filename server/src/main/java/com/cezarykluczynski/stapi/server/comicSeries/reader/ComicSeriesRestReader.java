package com.cezarykluczynski.stapi.server.comicSeries.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesResponse;
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries;
import com.cezarykluczynski.stapi.server.comicSeries.dto.ComicSeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesRestMapper;
import com.cezarykluczynski.stapi.server.comicSeries.query.ComicSeriesRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.Reader;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicSeriesRestReader implements Reader<ComicSeriesRestBeanParams, ComicSeriesResponse> {

	private ComicSeriesRestQuery comicSeriesRestQuery;

	private ComicSeriesRestMapper comicSeriesRestMapper;

	private PageMapper pageMapper;

	@Inject
	public ComicSeriesRestReader(ComicSeriesRestQuery comicSeriesRestQuery, ComicSeriesRestMapper comicSeriesRestMapper, PageMapper pageMapper) {
		this.comicSeriesRestQuery = comicSeriesRestQuery;
		this.comicSeriesRestMapper = comicSeriesRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public ComicSeriesResponse read(ComicSeriesRestBeanParams input) {
		Page<ComicSeries> comicSeriesPage = comicSeriesRestQuery.query(input);
		ComicSeriesResponse comicSeriesResponse = new ComicSeriesResponse();
		comicSeriesResponse.setPage(pageMapper.fromPageToRestResponsePage(comicSeriesPage));
		comicSeriesResponse.getComicSeries().addAll(comicSeriesRestMapper.map(comicSeriesPage.getContent()));
		return comicSeriesResponse;
	}

}
