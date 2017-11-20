package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionPortType;

public class BookCollection {

	private final BookCollectionPortType bookCollectionPortType;

	private final ApiKeySupplier apiKeySupplier;

	public BookCollection(BookCollectionPortType bookCollectionPortType, ApiKeySupplier apiKeySupplier) {
		this.bookCollectionPortType = bookCollectionPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public BookCollectionFullResponse get(BookCollectionFullRequest request) {
		apiKeySupplier.supply(request);
		return bookCollectionPortType.getBookCollectionFull(request);
	}

	public BookCollectionBaseResponse search(BookCollectionBaseRequest request) {
		apiKeySupplier.supply(request);
		return bookCollectionPortType.getBookCollectionBase(request);
	}

}
