package com.cezarykluczynski.stapi.server.comic_series.query;

import com.cezarykluczynski.stapi.model.comic_series.dto.ComicSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.comic_series.repository.ComicSeriesRepository;
import com.cezarykluczynski.stapi.server.comic_series.dto.ComicSeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.comic_series.mapper.ComicSeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ComicSeriesRestQuery {

	private final ComicSeriesBaseRestMapper comicSeriesBaseRestMapper;

	private final PageMapper pageMapper;

	private final ComicSeriesRepository comicSeriesRepository;

	public ComicSeriesRestQuery(ComicSeriesBaseRestMapper comicSeriesBaseRestMapper, PageMapper pageMapper,
			ComicSeriesRepository comicSeriesRepository) {
		this.comicSeriesBaseRestMapper = comicSeriesBaseRestMapper;
		this.pageMapper = pageMapper;
		this.comicSeriesRepository = comicSeriesRepository;
	}

	public Page<ComicSeries> query(ComicSeriesRestBeanParams comicSeriesRestBeanParams) {
		ComicSeriesRequestDTO comicSeriesRequestDTO = comicSeriesBaseRestMapper.mapBase(comicSeriesRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(comicSeriesRestBeanParams);
		return comicSeriesRepository.findMatching(comicSeriesRequestDTO, pageRequest);
	}

}
