package com.cezarykluczynski.stapi.client.api.soap;

import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesFullResponse;
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesPortType;

public class BookSeries {

	private final BookSeriesPortType bookSeriesPortType;

	private final ApiKeySupplier apiKeySupplier;

	public BookSeries(BookSeriesPortType bookSeriesPortType, ApiKeySupplier apiKeySupplier) {
		this.bookSeriesPortType = bookSeriesPortType;
		this.apiKeySupplier = apiKeySupplier;
	}

	public BookSeriesFullResponse get(BookSeriesFullRequest request) {
		apiKeySupplier.supply(request);
		return bookSeriesPortType.getBookSeriesFull(request);
	}

	public BookSeriesBaseResponse search(BookSeriesBaseRequest request) {
		apiKeySupplier.supply(request);
		return bookSeriesPortType.getBookSeriesBase(request);
	}

}
