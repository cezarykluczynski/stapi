package com.cezarykluczynski.stapi.server.book_series.query;

import com.cezarykluczynski.stapi.model.book_series.dto.BookSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries;
import com.cezarykluczynski.stapi.model.book_series.repository.BookSeriesRepository;
import com.cezarykluczynski.stapi.server.book_series.dto.BookSeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.book_series.mapper.BookSeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BookSeriesRestQuery {

	private final BookSeriesBaseRestMapper bookSeriesBaseRestMapper;

	private final PageMapper pageMapper;

	private final BookSeriesRepository bookSeriesRepository;

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
