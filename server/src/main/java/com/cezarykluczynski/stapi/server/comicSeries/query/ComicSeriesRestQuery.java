package com.cezarykluczynski.stapi.server.comicSeries.query;

import com.cezarykluczynski.stapi.model.comicSeries.dto.ComicSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.comicSeries.repository.ComicSeriesRepository;
import com.cezarykluczynski.stapi.server.comicSeries.dto.ComicSeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicSeriesRestQuery {

	private ComicSeriesRestMapper comicSeriesRestMapper;

	private PageMapper pageMapper;

	private ComicSeriesRepository comicSeriesRepository;

	@Inject
	public ComicSeriesRestQuery(ComicSeriesRestMapper comicSeriesRestMapper, PageMapper pageMapper,
			ComicSeriesRepository comicSeriesRepository) {
		this.comicSeriesRestMapper = comicSeriesRestMapper;
		this.pageMapper = pageMapper;
		this.comicSeriesRepository = comicSeriesRepository;
	}

	public Page<ComicSeries> query(ComicSeriesRestBeanParams comicSeriesRestBeanParams) {
		ComicSeriesRequestDTO comicSeriesRequestDTO = comicSeriesRestMapper.map(comicSeriesRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(comicSeriesRestBeanParams);
		return comicSeriesRepository.findMatching(comicSeriesRequestDTO, pageRequest);
	}

}
