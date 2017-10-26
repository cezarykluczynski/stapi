package com.cezarykluczynski.stapi.server.book.query;

import com.cezarykluczynski.stapi.client.v1.soap.BookBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookFullRequest;
import com.cezarykluczynski.stapi.model.book.dto.BookRequestDTO;
import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.model.book.repository.BookRepository;
import com.cezarykluczynski.stapi.server.book.mapper.BookBaseSoapMapper;
import com.cezarykluczynski.stapi.server.book.mapper.BookFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BookSoapQuery {

	private final BookBaseSoapMapper bookBaseSoapMapper;

	private final BookFullSoapMapper bookFullSoapMapper;

	private final PageMapper pageMapper;

	private final BookRepository bookRepository;

	public BookSoapQuery(BookBaseSoapMapper bookBaseSoapMapper, BookFullSoapMapper bookFullSoapMapper, PageMapper pageMapper,
			BookRepository bookRepository) {
		this.bookBaseSoapMapper = bookBaseSoapMapper;
		this.bookFullSoapMapper = bookFullSoapMapper;
		this.pageMapper = pageMapper;
		this.bookRepository = bookRepository;
	}

	public Page<Book> query(BookBaseRequest bookBaseRequest) {
		BookRequestDTO bookRequestDTO = bookBaseSoapMapper.mapBase(bookBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(bookBaseRequest.getPage());
		return bookRepository.findMatching(bookRequestDTO, pageRequest);
	}

	public Page<Book> query(BookFullRequest bookFullRequest) {
		BookRequestDTO bookRequestDTO = bookFullSoapMapper.mapFull(bookFullRequest);
		return bookRepository.findMatching(bookRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
