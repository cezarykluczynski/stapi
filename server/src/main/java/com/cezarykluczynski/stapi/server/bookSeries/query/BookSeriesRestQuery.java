package com.cezarykluczynski.stapi.server.bookSeries.query;

import com.cezarykluczynski.stapi.model.bookSeries.dto.BookSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.bookSeries.entity.BookSeries;
import com.cezarykluczynski.stapi.model.bookSeries.repository.BookSeriesRepository;
import com.cezarykluczynski.stapi.server.bookSeries.dto.BookSeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.bookSeries.mapper.BookSeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class BookSeriesRestQuery {

	private BookSeriesBaseRestMapper bookSeriesBaseRestMapper;

	private PageMapper pageMapper;

	private BookSeriesRepository bookSeriesRepository;

	@Inject
	public BookSeriesRestQuery(BookSeriesBaseRestMapper bookSeriesBaseRestMapper, PageMapper pageMapper, BookSeriesRepository bookSeriesRepository) {
		this.bookSeriesBaseRestMapper = bookSeriesBaseRestMapper;
		this.pageMapper = pageMapper;
		this.bookSeriesRepository = bookSeriesRepository;
	}

	public Page<BookSeries> query(BookSeriesRestBeanParams bookSeriesRestBeanParams) {
		BookSeriesRequestDTO bookSeriesRequestDTO = bookSeriesBaseRestMapper.mapBase(bookSeriesRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(bookSeriesRestBeanParams);
		return bookSeriesRepository.findMatching(bookSeriesRequestDTO, pageRequest);
	}

}
