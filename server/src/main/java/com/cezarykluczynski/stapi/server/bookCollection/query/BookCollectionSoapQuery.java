package com.cezarykluczynski.stapi.server.bookCollection.query;

import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFullRequest;
import com.cezarykluczynski.stapi.model.bookCollection.dto.BookCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.bookCollection.entity.BookCollection;
import com.cezarykluczynski.stapi.model.bookCollection.repository.BookCollectionRepository;
import com.cezarykluczynski.stapi.server.bookCollection.mapper.BookCollectionBaseSoapMapper;
import com.cezarykluczynski.stapi.server.bookCollection.mapper.BookCollectionFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class BookCollectionSoapQuery {

	private BookCollectionBaseSoapMapper bookCollectionBaseSoapMapper;

	private BookCollectionFullSoapMapper bookCollectionFullSoapMapper;

	private PageMapper pageMapper;

	private BookCollectionRepository bookCollectionRepository;

	@Inject
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
