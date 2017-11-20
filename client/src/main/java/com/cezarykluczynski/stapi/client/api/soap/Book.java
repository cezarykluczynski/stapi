package com.cezarykluczynski.stapi.client.api.soap;


import com.cezarykluczynski.stapi.client.v1.soap.BookBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.BookFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.BookPortType;

public class Book {

	private final BookPortType bookPortType;

	private final ApiKeySupplier apiKeySupplier;

	public Book(BookPortType bookPortType, ApiKeySupplier apiKeySupplier) {
		this.bookPortType = bookPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public BookFullResponse get(BookFullRequest request) {
		apiKeySupplier.supply(request);
		return bookPortType.getBookFull(request);
	}

	public BookBaseResponse search(BookBaseRequest request) {
		apiKeySupplier.supply(request);
		return bookPortType.getBookBase(request);
	}

}
