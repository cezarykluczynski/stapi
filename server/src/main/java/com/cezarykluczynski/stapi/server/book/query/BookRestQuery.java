package com.cezarykluczynski.stapi.server.book.query;

import com.cezarykluczynski.stapi.model.book.dto.BookRequestDTO;
import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.model.book.repository.BookRepository;
import com.cezarykluczynski.stapi.server.book.dto.BookRestBeanParams;
import com.cezarykluczynski.stapi.server.book.mapper.BookBaseRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BookRestQuery {

	private final BookBaseRestMapper bookBaseRestMapper;

	private final PageMapper pageMapper;

	private final BookRepository bookRepository;

	public BookRestQuery(BookBaseRestMapper bookBaseRestMapper, PageMapper pageMapper, BookRepository bookRepository) {
		this.bookBaseRestMapper = bookBaseRestMapper;
		this.pageMapper = pageMapper;
		this.bookRepository = bookRepository;
	}

	public Page<Book> query(BookRestBeanParams bookRestBeanParams) {
		BookRequestDTO bookRequestDTO = bookBaseRestMapper.mapBase(bookRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(bookRestBeanParams);
		return bookRepository.findMatching(bookRequestDTO, pageRequest);
	}

}
