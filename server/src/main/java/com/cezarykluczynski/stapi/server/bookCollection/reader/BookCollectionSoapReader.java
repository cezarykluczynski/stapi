package com.cezarykluczynski.stapi.server.bookCollection.reader;

import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFullResponse;
import com.cezarykluczynski.stapi.model.bookCollection.entity.BookCollection;
import com.cezarykluczynski.stapi.server.bookCollection.mapper.BookCollectionBaseSoapMapper;
import com.cezarykluczynski.stapi.server.bookCollection.mapper.BookCollectionFullSoapMapper;
import com.cezarykluczynski.stapi.server.bookCollection.query.BookCollectionSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class BookCollectionSoapReader implements BaseReader<BookCollectionBaseRequest, BookCollectionBaseResponse>,
		FullReader<BookCollectionFullRequest, BookCollectionFullResponse> {

	private final BookCollectionSoapQuery bookCollectionSoapQuery;

	private final BookCollectionBaseSoapMapper bookCollectionBaseSoapMapper;

	private final BookCollectionFullSoapMapper bookCollectionFullSoapMapper;

	private final PageMapper pageMapper;

	public BookCollectionSoapReader(BookCollectionSoapQuery bookCollectionSoapQuery, BookCollectionBaseSoapMapper bookCollectionBaseSoapMapper,
			BookCollectionFullSoapMapper bookCollectionFullSoapMapper, PageMapper pageMapper) {
		this.bookCollectionSoapQuery = bookCollectionSoapQuery;
		this.bookCollectionBaseSoapMapper = bookCollectionBaseSoapMapper;
		this.bookCollectionFullSoapMapper = bookCollectionFullSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public BookCollectionBaseResponse readBase(BookCollectionBaseRequest input) {
		Page<BookCollection> bookCollectionPage = bookCollectionSoapQuery.query(input);
		BookCollectionBaseResponse bookCollectionResponse = new BookCollectionBaseResponse();
		bookCollectionResponse.setPage(pageMapper.fromPageToSoapResponsePage(bookCollectionPage));
		bookCollectionResponse.getBookCollections().addAll(bookCollectionBaseSoapMapper.mapBase(bookCollectionPage.getContent()));
		return bookCollectionResponse;
	}

	@Override
	public BookCollectionFullResponse readFull(BookCollectionFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<BookCollection> bookCollectionPage = bookCollectionSoapQuery.query(input);
		BookCollectionFullResponse bookCollectionFullResponse = new BookCollectionFullResponse();
		bookCollectionFullResponse.setBookCollection(bookCollectionFullSoapMapper
				.mapFull(Iterables.getOnlyElement(bookCollectionPage.getContent(), null)));
		return bookCollectionFullResponse;
	}

}
