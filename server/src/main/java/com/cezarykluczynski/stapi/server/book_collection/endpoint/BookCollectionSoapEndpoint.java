package com.cezarykluczynski.stapi.server.book_collection.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.BookCollectionPortType;
import com.cezarykluczynski.stapi.server.book_collection.reader.BookCollectionSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class BookCollectionSoapEndpoint implements BookCollectionPortType {

	public static final String ADDRESS = "/v1/soap/bookCollection";

	private final BookCollectionSoapReader bookCollectionSoapReader;

	public BookCollectionSoapEndpoint(BookCollectionSoapReader bookCollectionSoapReader) {
		this.bookCollectionSoapReader = bookCollectionSoapReader;
	}

	@Override
	public BookCollectionBaseResponse getBookCollectionBase(@WebParam(partName = "request", name = "BookCollectionBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/bookCollection") BookCollectionBaseRequest request) {
		return bookCollectionSoapReader.readBase(request);
	}

	@Override
	public BookCollectionFullResponse getBookCollectionFull(@WebParam(partName = "request", name = "BookCollectionFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/bookCollection") BookCollectionFullRequest request) {
		return bookCollectionSoapReader.readFull(request);
	}

}
