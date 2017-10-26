package com.cezarykluczynski.stapi.server.book.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.BookBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.BookFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.BookPortType;
import com.cezarykluczynski.stapi.server.book.reader.BookSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class BookSoapEndpoint implements BookPortType {

	public static final String ADDRESS = "/v1/soap/book";

	private final BookSoapReader bookSoapReader;

	public BookSoapEndpoint(BookSoapReader bookSoapReader) {
		this.bookSoapReader = bookSoapReader;
	}

	@Override
	public BookBaseResponse getBookBase(@WebParam(partName = "request", name = "BookBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/book") BookBaseRequest request) {
		return bookSoapReader.readBase(request);
	}

	@Override
	public BookFullResponse getBookFull(@WebParam(partName = "request", name = "BookFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/book") BookFullRequest request) {
		return bookSoapReader.readFull(request);
	}

}
