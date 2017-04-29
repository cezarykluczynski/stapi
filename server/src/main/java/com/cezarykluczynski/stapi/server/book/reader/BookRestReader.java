package com.cezarykluczynski.stapi.server.book.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.BookBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookFullResponse;
import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.server.book.dto.BookRestBeanParams;
import com.cezarykluczynski.stapi.server.book.mapper.BookBaseRestMapper;
import com.cezarykluczynski.stapi.server.book.mapper.BookFullRestMapper;
import com.cezarykluczynski.stapi.server.book.query.BookRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class BookRestReader implements BaseReader<BookRestBeanParams, BookBaseResponse>, FullReader<String, BookFullResponse> {

	private BookRestQuery bookRestQuery;

	private BookBaseRestMapper bookBaseRestMapper;

	private BookFullRestMapper bookFullRestMapper;

	private PageMapper pageMapper;

	@Inject
	public BookRestReader(BookRestQuery bookRestQuery, BookBaseRestMapper bookBaseRestMapper, BookFullRestMapper bookFullRestMapper,
			PageMapper pageMapper) {
		this.bookRestQuery = bookRestQuery;
		this.bookBaseRestMapper = bookBaseRestMapper;
		this.bookFullRestMapper = bookFullRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public BookBaseResponse readBase(BookRestBeanParams bookRestBeanParams) {
		Page<Book> bookPage = bookRestQuery.query(bookRestBeanParams);
		BookBaseResponse bookResponse = new BookBaseResponse();
		bookResponse.setPage(pageMapper.fromPageToRestResponsePage(bookPage));
		bookResponse.getBooks().addAll(bookBaseRestMapper.mapBase(bookPage.getContent()));
		return bookResponse;
	}

	@Override
	public BookFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		BookRestBeanParams bookRestBeanParams = new BookRestBeanParams();
		bookRestBeanParams.setUid(uid);
		Page<Book> bookPage = bookRestQuery.query(bookRestBeanParams);
		BookFullResponse bookResponse = new BookFullResponse();
		bookResponse.setBook(bookFullRestMapper.mapFull(Iterables.getOnlyElement(bookPage.getContent(), null)));
		return bookResponse;
	}

}
