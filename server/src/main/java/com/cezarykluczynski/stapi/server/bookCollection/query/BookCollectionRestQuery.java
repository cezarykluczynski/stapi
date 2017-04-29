package com.cezarykluczynski.stapi.server.bookCollection.query;

import com.cezarykluczynski.stapi.model.bookCollection.dto.BookCollectionRequestDTO;
import com.cezarykluczynski.stapi.model.bookCollection.entity.BookCollection;
import com.cezarykluczynski.stapi.model.bookCollection.repository.BookCollectionRepository;
import com.cezarykluczynski.stapi.server.bookCollection.dto.BookCollectionRestBeanParams;
import com.cezarykluczynski.stapi.server.bookCollection.mapper.BookCollectionBaseRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class BookCollectionRestQuery {

	private BookCollectionBaseRestMapper bookCollectionBaseRestMapper;

	private PageMapper pageMapper;

	private BookCollectionRepository bookCollectionRepository;

	@Inject
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
