package com.cezarykluczynski.stapi.server.book_collection.query;

import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFullRequest;
import com.cezarykluczynski.stapi.model.book_collection.dto.BookCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection;
import com.cezarykluczynski.stapi.model.book_collection.repository.BookCollectionRepository;
import com.cezarykluczynski.stapi.server.book_collection.mapper.BookCollectionBaseSoapMapper;
import com.cezarykluczynski.stapi.server.book_collection.mapper.BookCollectionFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BookCollectionSoapQuery {

	private final BookCollectionBaseSoapMapper bookCollectionBaseSoapMapper;

	private final BookCollectionFullSoapMapper bookCollectionFullSoapMapper;

	private final PageMapper pageMapper;

	private final BookCollectionRepository bookCollectionRepository;

	public BookCollectionSoapQuery(BookCollectionBaseSoapMapper bookCollectionBaseSoapMapper,
			BookCollectionFullSoapMapper bookCollectionFullSoapMapper, PageMapper pageMapper, BookCollectionRepository bookCollectionRepository) {
		this.bookCollectionBaseSoapMapper = bookCollectionBaseSoapMapper;
		this.bookCollectionFullSoapMapper = bookCollectionFullSoapMapper;
		this.pageMapper = pageMapper;
		this.bookCollectionRepository = bookCollectionRepository;
	}

	public Page<BookCollection> query(BookCollectionBaseRequest bookCollectionBaseRequest) {
		BookCollectionRequestDTO bookCollectionRequestDTO = bookCollectionBaseSoapMapper.mapBase(bookCollectionBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(bookCollectionBaseRequest.getPage());
		return bookCollectionRepository.findMatching(bookCollectionRequestDTO, pageRequest);
	}

	public Page<BookCollection> query(BookCollectionFullRequest bookCollectionFullRequest) {
		BookCollectionRequestDTO bookCollectionRequestDTO = bookCollectionFullSoapMapper.mapFull(bookCollectionFullRequest);
		return bookCollectionRepository.findMatching(bookCollectionRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
