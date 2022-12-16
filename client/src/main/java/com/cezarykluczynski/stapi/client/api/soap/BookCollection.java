package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionPortType;

public class BookCollection {

	private final BookCollectionPortType bookCollectionPortType;

	public BookCollection(BookCollectionPortType bookCollectionPortType) {
		this.bookCollectionPortType = bookCollectionPortType;
	}

	@Deprecated
	public BookCollectionFullResponse get(BookCollectionFullRequest request) {
		return bookCollectionPortType.getBookCollectionFull(request);
	}

	@Deprecated
	public BookCollectionBaseResponse search(BookCollectionBaseRequest request) {
		return bookCollectionPortType.getBookCollectionBase(request);
	}

}
