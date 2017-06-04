package com.cezarykluczynski.stapi.server.book.reader;

import com.cezarykluczynski.stapi.client.v1.soap.BookBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.BookFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookFullResponse;
import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.server.book.mapper.BookBaseSoapMapper;
import com.cezarykluczynski.stapi.server.book.mapper.BookFullSoapMapper;
import com.cezarykluczynski.stapi.server.book.query.BookSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class BookSoapReader implements BaseReader<BookBaseRequest, BookBaseResponse>, FullReader<BookFullRequest, BookFullResponse> {

	private final BookSoapQuery bookSoapQuery;

	private final BookBaseSoapMapper bookBaseSoapMapper;

	private final BookFullSoapMapper bookFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public BookSoapReader(BookSoapQuery bookSoapQuery, BookBaseSoapMapper bookBaseSoapMapper, BookFullSoapMapper bookFullSoapMapper,
			PageMapper pageMapper, SortMapper sortMapper) {
		this.bookSoapQuery = bookSoapQuery;
		this.bookBaseSoapMapper = bookBaseSoapMapper;
		this.bookFullSoapMapper = bookFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public BookBaseResponse readBase(BookBaseRequest input) {
		Page<Book> bookPage = bookSoapQuery.query(input);
		BookBaseResponse bookResponse = new BookBaseResponse();
		bookResponse.setPage(pageMapper.fromPageToSoapResponsePage(bookPage));
		bookResponse.setSort(sortMapper.map(input.getSort()));
		bookResponse.getBooks().addAll(bookBaseSoapMapper.mapBase(bookPage.getContent()));
		return bookResponse;
	}

	@Override
	public BookFullResponse readFull(BookFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Book> bookPage = bookSoapQuery.query(input);
		BookFullResponse bookFullResponse = new BookFullResponse();
		bookFullResponse.setBook(bookFullSoapMapper.mapFull(Iterables.getOnlyElement(bookPage.getContent(), null)));
		return bookFullResponse;
	}

}
