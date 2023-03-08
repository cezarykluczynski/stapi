package com.cezarykluczynski.stapi.server.book.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.BookV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookV2FullResponse;
import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.server.book.dto.BookV2RestBeanParams;
import com.cezarykluczynski.stapi.server.book.mapper.BookBaseRestMapper;
import com.cezarykluczynski.stapi.server.book.mapper.BookFullRestMapper;
import com.cezarykluczynski.stapi.server.book.query.BookRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class BookV2RestReader implements BaseReader<BookV2RestBeanParams, BookV2BaseResponse>, FullReader<BookV2FullResponse> {

	private final BookRestQuery bookRestQuery;

	private final BookBaseRestMapper bookBaseRestMapper;

	private final BookFullRestMapper bookFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public BookV2RestReader(BookRestQuery bookRestQuery, BookBaseRestMapper bookBaseRestMapper,
			BookFullRestMapper bookFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.bookRestQuery = bookRestQuery;
		this.bookBaseRestMapper = bookBaseRestMapper;
		this.bookFullRestMapper = bookFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public BookV2BaseResponse readBase(BookV2RestBeanParams input) {
		Page<Book> bookPage = bookRestQuery.query(input);
		BookV2BaseResponse bookResponse = new BookV2BaseResponse();
		bookResponse.setPage(pageMapper.fromPageToRestResponsePage(bookPage));
		bookResponse.setSort(sortMapper.map(input.getSort()));
		bookResponse.getBooks().addAll(bookBaseRestMapper.mapV2Base(bookPage.getContent()));
		return bookResponse;
	}

	@Override
	public BookV2FullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		BookV2RestBeanParams bookV2RestBeanParams = new BookV2RestBeanParams();
		bookV2RestBeanParams.setUid(uid);
		Page<Book> bookPage = bookRestQuery.query(bookV2RestBeanParams);
		BookV2FullResponse bookResponse = new BookV2FullResponse();
		bookResponse.setBook(bookFullRestMapper.mapV2Full(Iterables.getOnlyElement(bookPage.getContent(), null)));
		return bookResponse;
	}

}
