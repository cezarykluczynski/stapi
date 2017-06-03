package com.cezarykluczynski.stapi.server.bookSeries.query;

import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesFullRequest;
import com.cezarykluczynski.stapi.model.bookSeries.dto.BookSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.bookSeries.entity.BookSeries;
import com.cezarykluczynski.stapi.model.bookSeries.repository.BookSeriesRepository;
import com.cezarykluczynski.stapi.server.bookSeries.mapper.BookSeriesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.bookSeries.mapper.BookSeriesFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class BookSeriesSoapQuery {

	private final BookSeriesBaseSoapMapper bookSeriesBaseSoapMapper;

	private final BookSeriesFullSoapMapper bookSeriesFullSoapMapper;

	private final PageMapper pageMapper;

	private final BookSeriesRepository bookSeriesRepository;

	@Inject
	public BookSeriesSoapQuery(BookSeriesBaseSoapMapper bookSeriesBaseSoapMapper, BookSeriesFullSoapMapper bookSeriesFullSoapMapper,
			PageMapper pageMapper, BookSeriesRepository bookSeriesRepository) {
		this.bookSeriesBaseSoapMapper = bookSeriesBaseSoapMapper;
		this.bookSeriesFullSoapMapper = bookSeriesFullSoapMapper;
		this.pageMapper = pageMapper;
		this.bookSeriesRepository = bookSeriesRepository;
	}

	public Page<BookSeries> query(BookSeriesBaseRequest bookSeriesBaseRequest) {
		BookSeriesRequestDTO bookSeriesRequestDTO = bookSeriesBaseSoapMapper.mapBase(bookSeriesBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(bookSeriesBaseRequest.getPage());
		return bookSeriesRepository.findMatching(bookSeriesRequestDTO, pageRequest);
	}

	public Page<BookSeries> query(BookSeriesFullRequest bookSeriesFullRequest) {
		BookSeriesRequestDTO bookSeriesRequestDTO = bookSeriesFullSoapMapper.mapFull(bookSeriesFullRequest);
		return bookSeriesRepository.findMatching(bookSeriesRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
