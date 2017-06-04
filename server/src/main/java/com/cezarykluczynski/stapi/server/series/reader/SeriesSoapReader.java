package com.cezarykluczynski.stapi.server.series.reader;

import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullResponse;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesFullSoapMapper;
import com.cezarykluczynski.stapi.server.series.query.SeriesSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SeriesSoapReader implements BaseReader<SeriesBaseRequest, SeriesBaseResponse>, FullReader<SeriesFullRequest, SeriesFullResponse> {

	private final SeriesSoapQuery seriesSoapQuery;

	private final SeriesBaseSoapMapper seriesBaseSoapMapper;

	private final SeriesFullSoapMapper seriesFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public SeriesSoapReader(SeriesSoapQuery seriesSoapQuery, SeriesBaseSoapMapper seriesBaseSoapMapper, SeriesFullSoapMapper seriesFullSoapMapper,
			PageMapper pageMapper, SortMapper sortMapper) {
		this.seriesSoapQuery = seriesSoapQuery;
		this.seriesBaseSoapMapper = seriesBaseSoapMapper;
		this.seriesFullSoapMapper = seriesFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public SeriesBaseResponse readBase(SeriesBaseRequest input) {
		Page<Series> seriesPage = seriesSoapQuery.query(input);
		SeriesBaseResponse seriesResponse = new SeriesBaseResponse();
		seriesResponse.setPage(pageMapper.fromPageToSoapResponsePage(seriesPage));
		seriesResponse.setSort(sortMapper.map(input.getSort()));
		seriesResponse.getSeries().addAll(seriesBaseSoapMapper.mapBase(seriesPage.getContent()));
		return seriesResponse;
	}

	@Override
	public SeriesFullResponse readFull(SeriesFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Series> seriesPage = seriesSoapQuery.query(input);
		SeriesFullResponse seriesFullResponse = new SeriesFullResponse();
		seriesFullResponse.setSeries(seriesFullSoapMapper.mapFull(Iterables.getOnlyElement(seriesPage.getContent(), null)));
		return seriesFullResponse;
	}

}
