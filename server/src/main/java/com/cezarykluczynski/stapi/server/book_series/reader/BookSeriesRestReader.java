package com.cezarykluczynski.stapi.server.book_series.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesFullResponse;
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries;
import com.cezarykluczynski.stapi.server.book_series.dto.BookSeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.book_series.mapper.BookSeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.book_series.mapper.BookSeriesFullRestMapper;
import com.cezarykluczynski.stapi.server.book_series.query.BookSeriesRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class BookSeriesRestReader implements BaseReader<BookSeriesRestBeanParams, BookSeriesBaseResponse>,
		FullReader<String, BookSeriesFullResponse> {

	private final BookSeriesRestQuery bookSeriesRestQuery;

	private final BookSeriesBaseRestMapper bookSeriesBaseRestMapper;

	private final BookSeriesFullRestMapper bookSeriesFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public BookSeriesRestReader(BookSeriesRestQuery bookSeriesRestQuery, BookSeriesBaseRestMapper bookSeriesBaseRestMapper,
			BookSeriesFullRestMapper bookSeriesFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.bookSeriesRestQuery = bookSeriesRestQuery;
		this.bookSeriesBaseRestMapper = bookSeriesBaseRestMapper;
		this.bookSeriesFullRestMapper = bookSeriesFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public BookSeriesBaseResponse readBase(BookSeriesRestBeanParams input) {
		Page<BookSeries> bookSeriesPage = bookSeriesRestQuery.query(input);
		BookSeriesBaseResponse bookSeriesResponse = new BookSeriesBaseResponse();
		bookSeriesResponse.setPage(pageMapper.fromPageToRestResponsePage(bookSeriesPage));
		bookSeriesResponse.setSort(sortMapper.map(input.getSort()));
		bookSeriesResponse.getBookSeries().addAll(bookSeriesBaseRestMapper.mapBase(bookSeriesPage.getContent()));
		return bookSeriesResponse;
	}

	@Override
	public BookSeriesFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		BookSeriesRestBeanParams bookSeriesRestBeanParams = new BookSeriesRestBeanParams();
		bookSeriesRestBeanParams.setUid(uid);
		Page<BookSeries> bookSeriesPage = bookSeriesRestQuery.query(bookSeriesRestBeanParams);
		BookSeriesFullResponse bookSeriesResponse = new BookSeriesFullResponse();
		bookSeriesResponse.setBookSeries(bookSeriesFullRestMapper.mapFull(Iterables.getOnlyElement(bookSeriesPage.getContent(), null)));
		return bookSeriesResponse;
	}

}
