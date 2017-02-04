package com.cezarykluczynski.stapi.server.comicSeries.reader;

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesResponse;
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesSoapMapper;
import com.cezarykluczynski.stapi.server.comicSeries.query.ComicSeriesSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.Reader;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ComicSeriesSoapReader implements Reader<ComicSeriesRequest, ComicSeriesResponse> {

	private ComicSeriesSoapQuery comicSeriesSoapQuery;

	private ComicSeriesSoapMapper comicSeriesSoapMapper;

	private PageMapper pageMapper;

	public ComicSeriesSoapReader(ComicSeriesSoapQuery comicSeriesSoapQuery, ComicSeriesSoapMapper comicSeriesSoapMapper,
			PageMapper pageMapper) {
		this.comicSeriesSoapQuery = comicSeriesSoapQuery;
		this.comicSeriesSoapMapper = comicSeriesSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public ComicSeriesResponse read(ComicSeriesRequest input) {
		Page<ComicSeries> comicSeriesPage = comicSeriesSoapQuery.query(input);
		ComicSeriesResponse comicSeriesResponse = new ComicSeriesResponse();
		comicSeriesResponse.setPage(pageMapper.fromPageToSoapResponsePage(comicSeriesPage));
		comicSeriesResponse.getComicSeries().addAll(comicSeriesSoapMapper.map(comicSeriesPage.getContent()));
		return comicSeriesResponse;
	}

}
