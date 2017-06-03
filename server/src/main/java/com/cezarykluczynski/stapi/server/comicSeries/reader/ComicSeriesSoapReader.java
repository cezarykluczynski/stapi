package com.cezarykluczynski.stapi.server.comicSeries.reader;

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullResponse;
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.comicSeries.mapper.ComicSeriesFullSoapMapper;
import com.cezarykluczynski.stapi.server.comicSeries.query.ComicSeriesSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ComicSeriesSoapReader implements BaseReader<ComicSeriesBaseRequest, ComicSeriesBaseResponse>,
		FullReader<ComicSeriesFullRequest, ComicSeriesFullResponse> {

	private final ComicSeriesSoapQuery comicSeriesSoapQuery;

	private final ComicSeriesBaseSoapMapper comicSeriesBaseSoapMapper;

	private final ComicSeriesFullSoapMapper comicSeriesFullSoapMapper;

	private final PageMapper pageMapper;

	public ComicSeriesSoapReader(ComicSeriesSoapQuery comicSeriesSoapQuery, ComicSeriesBaseSoapMapper comicSeriesBaseSoapMapper,
			ComicSeriesFullSoapMapper comicSeriesFullSoapMapper, PageMapper pageMapper) {
		this.comicSeriesSoapQuery = comicSeriesSoapQuery;
		this.comicSeriesBaseSoapMapper = comicSeriesBaseSoapMapper;
		this.comicSeriesFullSoapMapper = comicSeriesFullSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public ComicSeriesBaseResponse readBase(ComicSeriesBaseRequest input) {
		Page<ComicSeries> comicSeriesPage = comicSeriesSoapQuery.query(input);
		ComicSeriesBaseResponse comicSeriesResponse = new ComicSeriesBaseResponse();
		comicSeriesResponse.setPage(pageMapper.fromPageToSoapResponsePage(comicSeriesPage));
		comicSeriesResponse.getComicSeries().addAll(comicSeriesBaseSoapMapper.mapBase(comicSeriesPage.getContent()));
		return comicSeriesResponse;
	}

	@Override
	public ComicSeriesFullResponse readFull(ComicSeriesFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<ComicSeries> comicSeriesPage = comicSeriesSoapQuery.query(input);
		ComicSeriesFullResponse comicSeriesFullResponse = new ComicSeriesFullResponse();
		comicSeriesFullResponse.setComicSeries(comicSeriesFullSoapMapper.mapFull(Iterables.getOnlyElement(comicSeriesPage.getContent(), null)));
		return comicSeriesFullResponse;
	}

}
