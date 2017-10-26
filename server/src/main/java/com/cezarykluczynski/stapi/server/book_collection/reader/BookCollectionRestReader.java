package com.cezarykluczynski.stapi.server.book_collection.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.BookCollectionFullResponse;
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection;
import com.cezarykluczynski.stapi.server.book_collection.dto.BookCollectionRestBeanParams;
import com.cezarykluczynski.stapi.server.book_collection.mapper.BookCollectionBaseRestMapper;
import com.cezarykluczynski.stapi.server.book_collection.mapper.BookCollectionFullRestMapper;
import com.cezarykluczynski.stapi.server.book_collection.query.BookCollectionRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class BookCollectionRestReader implements BaseReader<BookCollectionRestBeanParams, BookCollectionBaseResponse>,
		FullReader<String, BookCollectionFullResponse> {

	private final BookCollectionRestQuery bookCollectionRestQuery;

	private final BookCollectionBaseRestMapper bookCollectionBaseRestMapper;

	private final BookCollectionFullRestMapper bookCollectionFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public BookCollectionRestReader(BookCollectionRestQuery bookCollectionRestQuery, BookCollectionBaseRestMapper bookCollectionBaseRestMapper,
			BookCollectionFullRestMapper bookCollectionFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.bookCollectionRestQuery = bookCollectionRestQuery;
		this.bookCollectionBaseRestMapper = bookCollectionBaseRestMapper;
		this.bookCollectionFullRestMapper = bookCollectionFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public BookCollectionBaseResponse readBase(BookCollectionRestBeanParams input) {
		Page<BookCollection> bookCollectionPage = bookCollectionRestQuery.query(input);
		BookCollectionBaseResponse bookCollectionResponse = new BookCollectionBaseResponse();
		bookCollectionResponse.setPage(pageMapper.fromPageToRestResponsePage(bookCollectionPage));
		bookCollectionResponse.setSort(sortMapper.map(input.getSort()));
		bookCollectionResponse.getBookCollections().addAll(bookCollectionBaseRestMapper.mapBase(bookCollectionPage.getContent()));
		return bookCollectionResponse;
	}

	@Override
	public BookCollectionFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		BookCollectionRestBeanParams bookCollectionRestBeanParams = new BookCollectionRestBeanParams();
		bookCollectionRestBeanParams.setUid(uid);
		Page<BookCollection> bookCollectionPage = bookCollectionRestQuery.query(bookCollectionRestBeanParams);
		BookCollectionFullResponse bookCollectionResponse = new BookCollectionFullResponse();
		bookCollectionResponse.setBookCollection(bookCollectionFullRestMapper
				.mapFull(Iterables.getOnlyElement(bookCollectionPage.getContent(), null)));
		return bookCollectionResponse;
	}

}
