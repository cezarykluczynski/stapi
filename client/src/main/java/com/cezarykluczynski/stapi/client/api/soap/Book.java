package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.BookBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.BookFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.BookPortType;

public class Book {

	private final BookPortType bookPortType;

	public Book(BookPortType bookPortType) {
		this.bookPortType = bookPortType;
	}

	@Deprecated
	public BookFullResponse get(BookFullRequest request) {
		return bookPortType.getBookFull(request);
	}

	@Deprecated
	public BookBaseResponse search(BookBaseRequest request) {
		return bookPortType.getBookBase(request);
	}

}
