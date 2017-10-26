package com.cezarykluczynski.stapi.server.book_collection.query;

import com.cezarykluczynski.stapi.model.book_collection.dto.BookCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection;
import com.cezarykluczynski.stapi.model.book_collection.repository.BookCollectionRepository;
import com.cezarykluczynski.stapi.server.book_collection.dto.BookCollectionRestBeanParams;
import com.cezarykluczynski.stapi.server.book_collection.mapper.BookCollectionBaseRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BookCollectionRestQuery {

	private final BookCollectionBaseRestMapper bookCollectionBaseRestMapper;

	private final PageMapper pageMapper;

	private final BookCollectionRepository bookCollectionRepository;

	public BookCollectionRestQuery(BookCollectionBaseRestMapper bookCollectionBaseRestMapper, PageMapper pageMapper,
			BookCollectionRepository bookCollectionRepository) {
		this.bookCollectionBaseRestMapper = bookCollectionBaseRestMapper;
		this.pageMapper = pageMapper;
		this.bookCollectionRepository = bookCollectionRepository;
	}

	public Page<BookCollection> query(BookCollectionRestBeanParams bookCollectionRestBeanParams) {
		BookCollectionRequestDTO bookCollectionRequestDTO = bookCollectionBaseRestMapper.mapBase(bookCollectionRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(bookCollectionRestBeanParams);
		return bookCollectionRepository.findMatching(bookCollectionRequestDTO, pageRequest);
	}

}
