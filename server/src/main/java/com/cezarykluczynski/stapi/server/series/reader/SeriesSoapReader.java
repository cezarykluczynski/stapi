package com.cezarykluczynski.stapi.server.series.reader;

import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullResponse;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesSoapMapper;
import com.cezarykluczynski.stapi.server.series.query.SeriesSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SeriesSoapReader implements BaseReader<SeriesBaseRequest, SeriesBaseResponse>, FullReader<SeriesFullRequest, SeriesFullResponse> {

	private SeriesSoapQuery seriesSoapQuery;

	private SeriesSoapMapper seriesSoapMapper;

	private PageMapper pageMapper;

	@Inject
	public SeriesSoapReader(SeriesSoapQuery seriesSoapQuery, SeriesSoapMapper seriesSoapMapper, PageMapper pageMapper) {
		this.seriesSoapQuery = seriesSoapQuery;
		this.seriesSoapMapper = seriesSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public SeriesBaseResponse readBase(SeriesBaseRequest input) {
		Page<Series> seriesPage = seriesSoapQuery.query(input);
		SeriesBaseResponse seriesBaseResponse = new SeriesBaseResponse();
		seriesBaseResponse.setPage(pageMapper.fromPageToSoapResponsePage(seriesPage));
		seriesBaseResponse.getSeries().addAll(seriesSoapMapper.mapBase(seriesPage.getContent()));
		return seriesBaseResponse;
	}

	@Override
	public SeriesFullResponse readFull(SeriesFullRequest input) {
		Page<Series> seriesPage = seriesSoapQuery.query(input);
		SeriesFullResponse seriesFullResponse = new SeriesFullResponse();
		seriesFullResponse.setSeries(seriesSoapMapper.mapFull(Iterables.getOnlyElement(seriesPage.getContent(), null)));
		return seriesFullResponse;
	}
}
