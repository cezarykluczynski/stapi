package com.cezarykluczynski.stapi.server.bookSeries.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesFullResponse;
import com.cezarykluczynski.stapi.model.bookSeries.entity.BookSeries;
import com.cezarykluczynski.stapi.server.bookSeries.dto.BookSeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.bookSeries.mapper.BookSeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.bookSeries.mapper.BookSeriesFullRestMapper;
import com.cezarykluczynski.stapi.server.bookSeries.query.BookSeriesRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class BookSeriesRestReader implements BaseReader<BookSeriesRestBeanParams, BookSeriesBaseResponse>,
		FullReader<String, BookSeriesFullResponse> {

	private BookSeriesRestQuery bookSeriesRestQuery;

	private BookSeriesBaseRestMapper bookSeriesBaseRestMapper;

	private BookSeriesFullRestMapper bookSeriesFullRestMapper;

	private PageMapper pageMapper;

	@Inject
	public BookSeriesRestReader(BookSeriesRestQuery bookSeriesRestQuery, BookSeriesBaseRestMapper bookSeriesBaseRestMapper,
			BookSeriesFullRestMapper bookSeriesFullRestMapper, PageMapper pageMapper) {
		this.bookSeriesRestQuery = bookSeriesRestQuery;
		this.bookSeriesBaseRestMapper = bookSeriesBaseRestMapper;
		this.bookSeriesFullRestMapper = bookSeriesFullRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public BookSeriesBaseResponse readBase(BookSeriesRestBeanParams bookSeriesRestBeanParams) {
		Page<BookSeries> bookSeriesPage = bookSeriesRestQuery.query(bookSeriesRestBeanParams);
		BookSeriesBaseResponse bookSeriesResponse = new BookSeriesBaseResponse();
		bookSeriesResponse.setPage(pageMapper.fromPageToRestResponsePage(bookSeriesPage));
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
