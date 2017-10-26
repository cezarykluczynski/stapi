package com.cezarykluczynski.stapi.server.book_series.endpoint;

import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesPortType;
import com.cezarykluczynski.stapi.server.book_series.reader.BookSeriesSoapReader;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;

@Service
public class BookSeriesSoapEndpoint implements BookSeriesPortType {

	public static final String ADDRESS = "/v1/soap/bookSeries";

	private final BookSeriesSoapReader bookSeriesSoapReader;

	public BookSeriesSoapEndpoint(BookSeriesSoapReader bookSeriesSoapReader) {
		this.bookSeriesSoapReader = bookSeriesSoapReader;
	}

	@Override
	public BookSeriesBaseResponse getBookSeriesBase(@WebParam(partName = "request", name = "BookSeriesBaseRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/bookSeries") BookSeriesBaseRequest request) {
		return bookSeriesSoapReader.readBase(request);
	}

	@Override
	public BookSeriesFullResponse getBookSeriesFull(@WebParam(partName = "request", name = "BookSeriesFullRequest",
			targetNamespace = "http://stapi.co/api/v1/soap/bookSeries") BookSeriesFullRequest request) {
		return bookSeriesSoapReader.readFull(request);
	}

}
