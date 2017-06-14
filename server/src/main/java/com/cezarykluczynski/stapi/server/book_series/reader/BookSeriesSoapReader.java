package com.cezarykluczynski.stapi.server.book_series.reader;

import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesFullResponse;
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries;
import com.cezarykluczynski.stapi.server.book_series.mapper.BookSeriesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.book_series.mapper.BookSeriesFullSoapMapper;
import com.cezarykluczynski.stapi.server.book_series.query.BookSeriesSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class BookSeriesSoapReader implements BaseReader<BookSeriesBaseRequest, BookSeriesBaseResponse>,
		FullReader<BookSeriesFullRequest, BookSeriesFullResponse> {

	private final BookSeriesSoapQuery bookSeriesSoapQuery;

	private final BookSeriesBaseSoapMapper bookSeriesBaseSoapMapper;

	private final BookSeriesFullSoapMapper bookSeriesFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public BookSeriesSoapReader(BookSeriesSoapQuery bookSeriesSoapQuery, BookSeriesBaseSoapMapper bookSeriesBaseSoapMapper,
			BookSeriesFullSoapMapper bookSeriesFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.bookSeriesSoapQuery = bookSeriesSoapQuery;
		this.bookSeriesBaseSoapMapper = bookSeriesBaseSoapMapper;
		this.bookSeriesFullSoapMapper = bookSeriesFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public BookSeriesBaseResponse readBase(BookSeriesBaseRequest input) {
		Page<BookSeries> bookSeriesPage = bookSeriesSoapQuery.query(input);
		BookSeriesBaseResponse bookSeriesResponse = new BookSeriesBaseResponse();
		bookSeriesResponse.setPage(pageMapper.fromPageToSoapResponsePage(bookSeriesPage));
		bookSeriesResponse.setSort(sortMapper.map(input.getSort()));
		bookSeriesResponse.getBookSeries().addAll(bookSeriesBaseSoapMapper.mapBase(bookSeriesPage.getContent()));
		return bookSeriesResponse;
	}

	@Override
	public BookSeriesFullResponse readFull(BookSeriesFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<BookSeries> bookSeriesPage = bookSeriesSoapQuery.query(input);
		BookSeriesFullResponse bookSeriesFullResponse = new BookSeriesFullResponse();
		bookSeriesFullResponse.setBookSeries(bookSeriesFullSoapMapper.mapFull(Iterables.getOnlyElement(bookSeriesPage.getContent(), null)));
		return bookSeriesFullResponse;
	}

}
